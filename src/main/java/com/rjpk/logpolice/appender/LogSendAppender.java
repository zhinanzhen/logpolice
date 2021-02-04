package com.rjpk.logpolice.appender;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.rjpk.logpolice.notice.NoticeService;
import com.rjpk.logpolice.notice.entity.ExceptionNotice;
import com.rjpk.logpolice.notice.factory.ExecutorFactory;
import com.rjpk.logpolice.properties.LogpoliceProperties;
import com.rjpk.logpolice.utils.ApplicationContextProvider;
import com.rjpk.logpolice.utils.LogpoliceConstant;
import com.rjpk.logpolice.utils.enums.ExecutorFactoryEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * @ClassName LogSendAppender
 * @Description 日志
 * @Author xuxiangnan
 * @Date 2021/1/28 15:23
 */
@Slf4j
public class LogSendAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private PatternLayout layout;

    @Override
    public void start() {
        PatternLayout patternLayout = new PatternLayout();
        patternLayout.setContext(this.context);
        patternLayout.setPattern(LogpoliceConstant.PROFILES_ACTIVE);
        patternLayout.start();
        this.layout = patternLayout;
        super.start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        if(!Objects.isNull(eventObject.getThrowableProxy())){
            LogpoliceProperties logpoliceProperties = ApplicationContextProvider.getBean(LogpoliceProperties.class);
            if (!logpoliceProperties.getEnabled()) {
                return;
            }
            ExecutorFactory executorFactory = ApplicationContextProvider.getBean(ExecutorFactory.class);
            executorFactory.getInstance(ExecutorFactoryEnum.NOTIFY).execute(() -> this.asyncAppender(eventObject, logpoliceProperties));
        }
    }

    private void asyncAppender(ILoggingEvent eventObject,LogpoliceProperties logpoliceProperties) {
        String logPattern = logpoliceProperties.getLogPattern();
        if (!Objects.equals(logpoliceProperties.getLogPattern(), layout.getPattern()) && !StringUtils.isEmpty(logPattern) && !StringUtils.isEmpty(logPattern.trim())) {
            PatternLayout patternLayout = new PatternLayout();
            patternLayout.setContext(this.context);
            patternLayout.setPattern(logPattern);
            patternLayout.start();
            this.layout = patternLayout;
            super.start();
        }

        String traceInfo;
        try {
            traceInfo = layout.doLayout(eventObject);
        } catch (Exception e) {
            log.info("LogSendAppender.append getTraceInfo error! {}", e.getMessage());
            return;
        }

        NoticeService noticeService = ApplicationContextProvider.getBean(NoticeService.class);
        ExceptionNotice exceptionNotice = new ExceptionNotice(logpoliceProperties.getProject(),
                getIpAddress(),
                traceInfo,
                eventObject,
                logpoliceProperties.getExceptionCacheKey(),
                !Objects.equals(LogpoliceConstant.PROFILES_ACTIVE, layout.getPattern()));
        noticeService.send(exceptionNotice, logpoliceProperties);
    }

    private String getIpAddress(){
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            log.info("LogSendAppender.getIpAddress Exception {}",e.getMessage());
        }
        return "";
    }
}
