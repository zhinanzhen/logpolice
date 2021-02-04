package com.rjpk.logpolice.utils;

/**
 * @ClassName LockUtils
 * @Description
 * @Author xuxiangnan
 * @Date 2021/1/28 17:25
 */
public interface LockUtils {
    /**
     * 加锁
     *
     * @param key 键
     * @return 是否成功
     */
    boolean lock(String key);

    /**
     * 解锁
     *
     * @param key 键
     */
    void unlock(String key);
}
