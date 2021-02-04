package com.rjpk.logpolice.test;

import com.rjpk.logpolice.utils.ConcurrentCache;

/**
 * @ClassName DemoCache
 * @Description
 * @Author xuxiangnan
 * @Date 2021/1/30 15:08
 */
public class DemoCache {
    public static void main(String[] args) {
        ConcurrentCache cache = new ConcurrentCache(3);
        //LinkedHashMapCache cache1 = new LinkedHashMapCache.Builder<String,Integer>(3).build();
        //WeakHashMapCache cache2 = new WeakHashMapCache.Builder<String,String>(3).build();
        DemoCache demoCache = new DemoCache();
        for (int i = 0; i < 5; i++) {
            A a = demoCache.new A(i);
            cache.put(i+"",a);
            a.start();
        }
        System.out.println("++++++++++++++++++++");
        System.gc();
        for (int i = 0; i < 5; i++) {
            System.out.println(cache.get(i + ""));
        }
        System.out.println("---------------------------------");
        cache.put(11+"",11);
        for (int i = 0; i < 13; i++) {
            System.out.println(cache.get(i + ""));
        }
    }

    class A extends Thread{
        private int time;
        public A(int time){
            this.time = time;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(this.time*500);
                System.out.println("dddddd:" + time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return String.valueOf(this.time);
        }
    }

}
