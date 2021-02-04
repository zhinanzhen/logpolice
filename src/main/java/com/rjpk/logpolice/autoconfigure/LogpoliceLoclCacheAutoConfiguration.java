package com.rjpk.logpolice.autoconfigure;

import com.rjpk.logpolice.notice.channel.ExceptionStatisticLocalCache;
import com.rjpk.logpolice.utils.ConcurrentCache;
import com.rjpk.logpolice.utils.LockUtilsLocalCache;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName LogpoliceLoclCacheAutoConfiguration
 * @Description 缓存
 * @Author xuxiangnan
 * @Date 2021/1/28 20:35
 */
@ConditionalOnProperty(name = "log.police.cache", havingValue = "local")
@Configuration
@AutoConfigureAfter({LogpoliceMailAutoConfiguration.class})
public class LogpoliceLoclCacheAutoConfiguration {
    @Bean
    public ExceptionStatisticLocalCache exceptionStatisticLocalCache() {
        return new ExceptionStatisticLocalCache(new ConcurrentCache<>(3));
    }

    @Bean
    public LockUtilsLocalCache lockUtilsLocalCache() {
        return new LockUtilsLocalCache(new ConcurrentHashMap<>());
    }
}
