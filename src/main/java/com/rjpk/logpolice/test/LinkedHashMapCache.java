package com.rjpk.logpolice.test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName LinkedHashMapCache
 * @Description
 * @Author xuxiangnan
 * @Date 2021/1/30 15:10
 */
public class LinkedHashMapCache<K,V> {
    private  final Map<K,V> eden;


    public LinkedHashMapCache(Builder builder) {
        this.eden = builder.eden;
    }

    public static class Builder<K,V> {

        private volatile  Map<K,V> eden;
        private int size;

        public Builder(int size){
            this.size = rangeCheck(size,Integer.MAX_VALUE,"缓存容器初始化容量异常");

            this.eden = new LinkedHashMap<K,V>(size,0.75f,true){
                @Override
                protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                    return size() >= size;
                }
            };
        }

        private static int rangeCheck(int val, int i, String arg) {
            if (val < 0 || val > i) {
                throw new IllegalArgumentException(arg + ":" + val);
            }
            return  val;
        }

        public LinkedHashMapCache build(){
            return new LinkedHashMapCache(this);
        }
    }

    public V get(K k){
        return eden.get(k);
    }

    public void put(K k,V v){
        this.eden.put(k,v);
    }

    public static void main(String[] args) {
        LinkedHashMapCache cache = new LinkedHashMapCache.Builder<String,Integer>(3).build();
        for (int i = 0; i < 5; i++) {
            cache.put(i+"",i);
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(cache.get(i + ""));
        }
    }

}
