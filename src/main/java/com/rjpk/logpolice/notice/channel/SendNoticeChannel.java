package com.rjpk.logpolice.notice.channel;

import com.rjpk.logpolice.notice.entity.ExceptionNotice;
import com.rjpk.logpolice.utils.enums.NoticeSendEnum;

/**
 * @ClassName SendNoticeChannel
 * @Description 发送渠道
 * @Author xuxiangnan
 * @Date 2021/1/28 13:44
 */
public interface SendNoticeChannel {
    /**
     * 获取消息类型
     *
     * @return 消息类型
     */
    NoticeSendEnum getType();

    /**
     * 推送消息
     *
     * @param exceptionNotice 异常信息
     */
    void send(ExceptionNotice exceptionNotice);
}
