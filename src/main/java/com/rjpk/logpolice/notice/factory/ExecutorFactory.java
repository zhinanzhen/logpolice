package com.rjpk.logpolice.notice.factory;

import com.rjpk.logpolice.utils.enums.ExecutorFactoryEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ExecutorFactory
 * @Description
 * @Author xuxiangnan
 * @Date 2021/1/28 15:38
 */
@Slf4j
public class ExecutorFactory {
    private final Map<ExecutorFactoryEnum, ThreadPoolExecutor> threadPoolExecutorMap;

    public ExecutorFactory(Map<ExecutorFactoryEnum, ThreadPoolExecutor> threadPoolExecutorMap) {
        this.threadPoolExecutorMap = threadPoolExecutorMap;
        Arrays.stream(ExecutorFactoryEnum.values()).forEach(this::init);
    }

    private ThreadPoolExecutor init(ExecutorFactoryEnum executorFactoryEnum) {
        ThreadFactory threadFactory = (new BasicThreadFactory.Builder()).namingPattern("logpolice-" + executorFactoryEnum.name() + "-thread-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(executorFactoryEnum.getCorePoolSize(),
                executorFactoryEnum.getMaximumPoolSize(),
                executorFactoryEnum.getKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(executorFactoryEnum.getQueueCapacity()),
                threadFactory,
                new ThreadPoolExecutor.DiscardPolicy());
        log.info("LogpoliceExecutorFactory.init threadPoolExecutor success, code:{}", executorFactoryEnum.name());
        return threadPoolExecutorMap.put(executorFactoryEnum, executor);
    }

    public ThreadPoolExecutor getInstance(ExecutorFactoryEnum executorFactoryEnum) {
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorMap.get(executorFactoryEnum);
        if (Objects.isNull(threadPoolExecutor)) {
            threadPoolExecutor = init(executorFactoryEnum);
        }
        return threadPoolExecutor;
    }
}
