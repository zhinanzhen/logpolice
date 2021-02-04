package com.rjpk.logpolice.properties;

import com.rjpk.logpolice.notice.entity.NoticeFrequencyType;
import com.rjpk.logpolice.utils.LogpoliceConstant;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName LogpoliceProperties
 * @Description 预警邮件配置
 * @Author xuxiangnan
 * @Date 2021/1/28 16:47
 */
@ConfigurationProperties("log.police.properties")
@Setter
@Getter
public class LogpoliceProperties {
    /**
     * 获取工程名
     */
    private String project = LogpoliceConstant.PROJECT;
    /**
     * 日志报警是否打开
     */
    private Boolean enabled = Boolean.FALSE;
    /**
     * 日志报警清除时间
     */
    private Long cleanTimeInterval = LogpoliceConstant.CLEAN_TIME_INTERVAL;
    /**
     * 通知间隔，此次出现相同的异常时，与上次通知的时间做对比，假如超过此设定的值，则再次通知
     */
    private Long noticeTimeInterval = LogpoliceConstant.NOTICE_TIME_INTERVAL;

    /**
     * 异常白名单 多个使用逗号隔开
     */
    private String exceptionWhiteList;
    /**
     * 类白名单 多个使用逗号隔开
     */
    private String classWhiteList;

    /**
     * 收件人
     */
    private String toMails;

    public Set<String> getClassWhiteList() {
        if(StringUtils.isNotEmpty(classWhiteList)){
            String[] split = this.classWhiteList.split(",");
            return new HashSet<>(Arrays.asList(split));
        }
        return Collections.emptySet();
    }

    public Set<String> getExceptionWhiteList() {
        if(StringUtils.isNotEmpty(exceptionWhiteList)){
            String[] split = this.exceptionWhiteList.split(",");
            return new HashSet<>(Arrays.asList(split));
        }
        return Collections.emptySet();
    }

    /**
     * 通知频率类型：按时间或按次数
     */
    public NoticeFrequencyType getFrequencyType() {
        return NoticeFrequencyType.TIMEOUT;
    }
    /**
     * 此次出现相同异常时，与上次通知的出现次数作对比，假如超过此设定的值，则再次通知
     */
    public Long getShowCount() {
        return LogpoliceConstant.SHOW_COUNT;
    }

    /**
     * 缓存 cache key
     */
    public String getExceptionCacheKey() {
        return LogpoliceConstant.EXCEPTION_CACHE_KEY;
    }

    /**
     * 获取模板格式
     */
    public String getLogPattern() {
        return LogpoliceConstant.PROFILES_ACTIVE;
    }
}
