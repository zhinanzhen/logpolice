package com.rjpk.logpolice.utils;

import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ConcurrentCache
 * @Description
 * @Author xuxiangnan
 * @Date 2021/1/30 15:03
 */
public final class ConcurrentCache<K,V> {
    private final int size;

    private final Map<K,V> eden;

    private final Map<K,V> longterm;

    public ConcurrentCache(int size){
        this.size = size;
        this.eden = new ConcurrentHashMap<>(size);
        this.longterm = new WeakHashMap<>(size);
    }

    public V get(K k){
        V v = this.eden.get(k);
        if (Objects.isNull(v)){
            synchronized (longterm){
                v = this.longterm.get(k);
            }
            if (Objects.nonNull(v)){
                this.eden.put(k,v);
            }
        }
        return v;
    }
    public void put(K k,V v){
        if (this.eden.size() >= size){
            synchronized (longterm){
                this.longterm.putAll(this.eden);
            }
            this.eden.clear();
        }
        this.eden.put(k,v);
    }

    public int size(){
        return this.size;
    }

}
