package com.rjpk.logpolice.utils;

/**
 * @ClassName LogpoliceConstant
 * @Description 日志报警配置
 * @Author xuxiangnan
 * @Date 2021/1/28 15:33
 */
public class LogpoliceConstant {
    /**
     * 默认工程名
     */
    public static final String PROJECT = "暂无获取";

    /**
     * 默认日志报警清除时间(s)-6h
     */
    public static final Long CLEAN_TIME_INTERVAL = 21600L;

    /**
     * 默认异常间隔时间(s)-10m
     */
    public static final Long NOTICE_TIME_INTERVAL = 600L;

    /**
     * 默认异常间隔次数
     */
    public static final Long SHOW_COUNT = 10L;

    /**
     * 异常追踪头
     */
    public static final String PROFILES_ACTIVE = " ";

    /**
     * 缓存
     */
    public static final String EXCEPTION_CACHE_KEY = "logpolice_exception_statistic:";

    /**
     * 邮件规则匹配
     */
    public static final String MAIL_PATTERN_MATCHES = "^[A-Za-z0-9_\\-]+@[a-zA-Z0-9_\\-]+(\\.[a-zA-Z]{2,4})+$";

    /**
     * 锁最大重试次数
     */
    public static final int LOCK_MAX_RETRY_NUM = 3;
    /**
     * 异常类型保存在内存中的最大数量，超过这个值，垃圾回收则会清理数据
    */
    public static final int MAX_EXCEPTION_TYPE_SIZE = 1000;
}
