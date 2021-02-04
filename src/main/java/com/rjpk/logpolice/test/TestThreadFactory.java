package com.rjpk.logpolice.test;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

/**
 * @ClassName TestThreadFactory
 * @Description
 * @Author xuxiangnan
 * @Date 2021/1/28 15:44
 */
public class TestThreadFactory {
    private static final Logger logger = LoggerFactory.getLogger(TestThreadFactory.class);

    /**
     * Spring 框架提供的
     */
    @Test
    public void testCustomizableThreadFactory(){
        logger.info("--记忆中的颜色是什么颜色---");
        ThreadFactory springThreadFactory = new CustomizableThreadFactory("springThread-pool-");
        ExecutorService exec = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10),springThreadFactory);
        exec.submit(() -> {
            logger.info("--记忆中的颜色是什么颜色---");
        });
    }

    /**
     * Apache commons-lang3
     */
    @Test
    public void testBasicThreadFactory(){
        logger.info("--记忆中的颜色是什么颜色---");
        ThreadFactory basicThreadFactory = new BasicThreadFactory.Builder().namingPattern("logpolice-thread-%d").build();

        ExecutorService exec = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10),basicThreadFactory );
        exec.submit(() -> {
            logger.info("--记忆中的颜色是什么颜色---");
        });
    }

    /**
     * Google guava
     */
    @Test
    public void testThreadFactoryBuilder(){
        /*ThreadFactory guavaThreadFactory = new ThreadFactoryBuilder().setNameFormat("retryClient-pool-").build();
        ExecutorService exec = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10),guavaThreadFactory );
        exec.submit(() -> {
            logger.info("--记忆中的颜色是什么颜色---");
        });*/
    }

    /**
     * 拒绝策略（终止-抛出异常、使用当前线程执行、丢弃-老、丢弃）
     */
    @Test
    public void testThreadPoolExecutor1(){
        logger.info("start");
        int corePoolSize = 5;
        int maxPoolSize = 100;
        long keepAliveTime = 5;
        ThreadFactory threadFactory = (new BasicThreadFactory.Builder()).namingPattern("logpolice-thread-%d").build();
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(1);
        //拒绝策略1：将抛出 RejectedExecutionException.
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS,
                queue,threadFactory, handler);
        for(int i=0; i<100; i++) {
            int j = i;
            executor.execute(()->{
                logger.info(j + " is running");
            });
        }
        executor.shutdown();
    }

    /**
     * 队列
     */
    @Test
    public void testThreadPoolExecutor2(){
        logger.info("start");
        int corePoolSize = 0;
        int maxPoolSize = Integer.MAX_VALUE;
        long keepAliveTime = 60L;
        ThreadFactory threadFactory = (new BasicThreadFactory.Builder()).namingPattern("logpolice-thread-%d").build();
        SynchronousQueue<Runnable> queue = new SynchronousQueue<Runnable>();
        //拒绝策略1：将抛出 RejectedExecutionException.
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS,
                queue,threadFactory, handler);
        for(int i=0; i<100; i++) {
            int j = i;
            executor.execute(()->{
                logger.info(j + " is running");
            });
        }
        executor.shutdown();
    }

}
