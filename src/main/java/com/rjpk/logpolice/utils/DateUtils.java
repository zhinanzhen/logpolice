package com.rjpk.logpolice.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

 /**
  * @ClassName DateUtils
  * @Description
  * @Author xuxiangnan
  * @Date 2021/1/29 9:09
  */
public class DateUtils {

    /**
     * 格式化时间
     */
    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间戳转换时间格式
     *
     * @param timeStamp 时间戳
     * @return 时间格式
     */
    public static LocalDateTime getLocalDateTime(Long timeStamp) {
        Instant instant = Instant.ofEpochMilli(timeStamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * 时间格式化
     *
     * @param localDateTime 时间
     * @return 格式化时间
     */
    public static String format(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dateTimeFormatter.format(localDateTime);
    }
}
