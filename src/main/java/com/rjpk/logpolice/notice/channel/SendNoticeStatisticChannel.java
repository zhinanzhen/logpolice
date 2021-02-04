package com.rjpk.logpolice.notice.channel;

import com.rjpk.logpolice.notice.entity.ExceptionStatistic;

import java.util.Optional;

/**
 * @ClassName SendNoticeChannel
 * @Description 发送渠道
 * @Author xuxiangnan
 * @Date 2021/1/28 13:44
 */
public interface SendNoticeStatisticChannel {
    /**
     * 查询异常统计
     *
     * @param openId 唯一标识
     * @return 异常统计
     */
    Optional<ExceptionStatistic> findByOpenId(String openId);

    /**
     * 保存异常统计
     *
     * @param openId             唯一标识
     * @param exceptionStatistic 异常统计
     * @return 是否成功
     */
    boolean save(String openId, ExceptionStatistic exceptionStatistic);
}
