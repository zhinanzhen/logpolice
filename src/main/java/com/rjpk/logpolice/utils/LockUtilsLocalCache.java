package com.rjpk.logpolice.utils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

 /**
  * @ClassName LockUtilsLocalCache
  * @Description 内存锁
  * @Author xuxiangnan
  * @Date 2021/1/28 20:36
  */
public class LockUtilsLocalCache implements LockUtils {

    private final Map<String, AtomicBoolean> versionMap;

    public LockUtilsLocalCache(Map<String, AtomicBoolean> versionMap) {
        this.versionMap = versionMap;
    }

    @Override
    public boolean lock(String key) {
        AtomicBoolean flag = versionMap.computeIfAbsent(key, v -> new AtomicBoolean(Boolean.TRUE));
        return flag.compareAndSet(Boolean.TRUE,Boolean.FALSE);
    }

    @Override
    public void unlock(String key) {
        versionMap.remove(key);
    }
}
