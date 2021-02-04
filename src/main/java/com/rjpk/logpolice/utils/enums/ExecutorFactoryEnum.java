package com.rjpk.logpolice.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

 /**
  * @ClassName ExecutorFactoryEnum
  * @Description 线程池
  * @Author xuxiangnan
  * @Date 2021/1/28 15:39
  */
@Getter
@AllArgsConstructor
public enum ExecutorFactoryEnum {

    /**
     * NOTIFY：消息推送
     */
    NOTIFY(50, 50, 0, 3000);

    private int corePoolSize;
    private int maximumPoolSize;
    private int keepAliveTime;
    private int queueCapacity;

}
