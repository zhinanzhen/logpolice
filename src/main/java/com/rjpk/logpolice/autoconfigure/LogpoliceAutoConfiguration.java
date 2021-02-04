package com.rjpk.logpolice.autoconfigure;

import com.rjpk.logpolice.notice.NoticeService;
import com.rjpk.logpolice.notice.factory.ExecutorFactory;
import com.rjpk.logpolice.notice.factory.NoticeServiceFactory;
import com.rjpk.logpolice.properties.LogpoliceProperties;
import com.rjpk.logpolice.utils.ApplicationContextProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName LogpoliceAutoConfiguration
 * @Description 日志配置自动装配
 * @Author xuxiangnan
 * @Date 2021/1/28 14:37
 */
@Configuration
@Import({ApplicationContextProvider.class, LogpoliceProperties.class})
public class LogpoliceAutoConfiguration {
    @Bean
    public ExecutorFactory logpoliceExecutorFactory() {
        return new ExecutorFactory(new ConcurrentHashMap<>());
    }

    @Bean
    public NoticeServiceFactory noticeServiceFactory() {
        return new NoticeServiceFactory();
    }

    @Bean
    public NoticeService noticeService() {
        return new NoticeService(noticeServiceFactory());
    }
}
