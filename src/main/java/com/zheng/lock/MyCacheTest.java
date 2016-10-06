package com.zheng.lock;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by zhenglian on 2016/10/1.
 */
public class MyCacheTest {

    private MyCache cache = new MyCache();

    public static void main(String[] args) {
        new MyCacheTest().init();
    }

    private void init() {
        final String key = "hello";
        for(int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                public void run() {
                    System.out.println(cache.get(key));
                }
            }).start();
        }
    }

    /**
     * 自定义缓存系统
     * 支持多线程并发
     */
    class MyCache {
        private final Map<String, Object> myCache = Maps.newHashMap();
        private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        public Object get(String key) {
            Object value = null;
            lock.readLock().lock();
            try {
                value = myCache.get(key);
                if(value == null) {
                    lock.readLock().unlock();
                    lock.writeLock().lock();
                    //执行数据库查询操作，获取数据并缓存
                    try {
                        value = myCache.get(key);
                        if(value == null) { //这里是为了避免多个线程同时进来查询数据，所以在检查一次
                            System.out.println("从数据库中查询" + key + "对应的数据...");
                            value = "world";
                            myCache.put(key, value);
                        }
                    } finally {
                        lock.readLock().lock();
                        lock.writeLock().unlock(); //这里设置为更新锁
                    }
                }
            }finally {
                lock.readLock().unlock();
            }
            return value;
        }
    }



}
