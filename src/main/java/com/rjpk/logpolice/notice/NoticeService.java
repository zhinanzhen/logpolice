package com.rjpk.logpolice.notice;

import com.rjpk.logpolice.notice.channel.SendNoticeChannel;
import com.rjpk.logpolice.notice.channel.SendNoticeStatisticChannel;
import com.rjpk.logpolice.notice.entity.ExceptionNotice;
import com.rjpk.logpolice.notice.entity.ExceptionStatistic;
import com.rjpk.logpolice.notice.entity.NoticeFrequencyType;
import com.rjpk.logpolice.notice.factory.NoticeServiceFactory;
import com.rjpk.logpolice.properties.LogpoliceProperties;
import com.rjpk.logpolice.utils.LockUtils;
import com.rjpk.logpolice.utils.LogpoliceConstant;
import lombok.extern.slf4j.Slf4j;

 /**
  * @ClassName NoticeService
  * @Description
  * @Author xuxiangnan
  * @Date 2021/1/28 17:36
  */
@Slf4j
public class NoticeService {

    private final NoticeServiceFactory noticeServiceFactory;

    public NoticeService(NoticeServiceFactory noticeServiceFactory) {
        this.noticeServiceFactory = noticeServiceFactory;
    }

    public void send(ExceptionNotice exceptionNotice, LogpoliceProperties logpoliceProperties) {
        // 根据数据存储类型获取锁工具
        boolean succeed = false;
        String openId = exceptionNotice.getOpenId();
        LockUtils lockUtils = noticeServiceFactory.getLockUtils();
        try {
            for (int i = 0; i < LogpoliceConstant.LOCK_MAX_RETRY_NUM; i++) {
                if (!lockUtils.lock(openId)) {
                    continue;
                }
                exceptionNotice = this.saveExceptionNotice(exceptionNotice, logpoliceProperties);
                succeed = true;
                break;
            }
        } finally {
            lockUtils.unlock(openId);
        }

        // 判断是否符合推送条件，可以优化异步推送
        if (succeed && exceptionNotice.isSend()) {
            SendNoticeChannel noticeChannel = noticeServiceFactory.getNoticeChannel();
            noticeChannel.send(exceptionNotice);
            log.info("noticeService.send success, openId:{}", openId);
        }
    }

    private ExceptionNotice saveExceptionNotice(ExceptionNotice exceptionNotice, LogpoliceProperties logpoliceProperties) {
        // 判断是否包含白名单
        if (exceptionNotice.containsWhiteList(logpoliceProperties.getExceptionWhiteList(), logpoliceProperties.getClassWhiteList())) {
            log.warn("logSendAppender.append exceptionWhiteList skip, exception:{}", exceptionNotice);
            return exceptionNotice;
        }

        // 查询异常数据，不存在则初始化
        SendNoticeStatisticChannel sendNoticeStatisticChannel = noticeServiceFactory.getSendNoticeStatisticChannel();
        String openId = exceptionNotice.getOpenId();
        ExceptionStatistic exceptionStatistic = sendNoticeStatisticChannel.findByOpenId(openId).orElse(new ExceptionStatistic(openId));

        // 判断异常数据是否超时重置
        if (exceptionStatistic.isTimeOut(logpoliceProperties.getCleanTimeInterval())) {
            log.info("noticeService.send timeOut, prepare resetData openId:{}", openId);
            exceptionStatistic.reset();
        }
        exceptionStatistic.pushOne();

        // 判断异常数据是否符合推送
        boolean isCheck;
        if (isCheck = NoticeFrequencyType.check(exceptionStatistic, logpoliceProperties)) {
            log.info("noticeService.send prepare, openId:{}", openId);
            exceptionStatistic.updateData();
            exceptionNotice.updateData(exceptionStatistic.getShowCount(),
                    exceptionStatistic.getNoticeTime(),
                    exceptionStatistic.getFirstTime());
        }
        boolean isSuccess = sendNoticeStatisticChannel.save(openId, exceptionStatistic);
        exceptionNotice.updateSend(isCheck, isSuccess);
        return exceptionNotice;
    }

}
