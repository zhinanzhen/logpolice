package com.rjpk.logpolice.notice.entity;

import com.rjpk.logpolice.properties.LogpoliceProperties;

import java.time.Duration;
import java.time.LocalDateTime;

/**
  * @ClassName NoticeFrequencyType
  * @Description 消息推送策略
  * @Author xuxiangnan
  * @Date 2021/1/28 16:50
  */
public enum NoticeFrequencyType {

    /**
     * TIMEOUT：超时
     * SHOW_COUNT：次数
     */
    TIMEOUT {
        @Override
        public boolean checkSend(ExceptionStatistic exceptionStatistics, LogpoliceProperties logpoliceProperties) {
            Duration dur = Duration.between(exceptionStatistics.getNoticeTime(), LocalDateTime.now());
            return Duration.ofSeconds(logpoliceProperties.getNoticeTimeInterval()).compareTo(dur) < 0;
        }
    },
    SHOW_COUNT {
        @Override
        public boolean checkSend(ExceptionStatistic exceptionStatistics, LogpoliceProperties logpoliceProperties) {
            long noticeShowCount = exceptionStatistics.getShowCount().longValue() - exceptionStatistics.getLastShowedCount();
            return noticeShowCount > logpoliceProperties.getShowCount();
        }

    };

    public boolean checkSend(ExceptionStatistic exceptionStatistics, LogpoliceProperties logpoliceProperties) {
        return false;
    }

    /**
     * 通知频率，在一定时间或者次数之内不再发送
     * @param exceptionStatistic
     * @param logpoliceProperties
     * @return
     */
    public static boolean check(ExceptionStatistic exceptionStatistic, LogpoliceProperties logpoliceProperties) {
        NoticeFrequencyType frequencyType = logpoliceProperties.getFrequencyType();
        return exceptionStatistic.isFirst() || frequencyType.checkSend(exceptionStatistic, logpoliceProperties);
    }
}
