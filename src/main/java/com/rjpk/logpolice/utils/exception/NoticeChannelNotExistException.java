package com.rjpk.logpolice.utils.exception;

/**
 * @ClassName NoticeChannelNotExistException
 * @Description
 * @Author xuxiangnan
 * @Date 2021/1/28 17:32
 */
public class NoticeChannelNotExistException extends RuntimeException{
    public NoticeChannelNotExistException(String message, Object... args) {
        super(String.format(message, args));
    }
}
