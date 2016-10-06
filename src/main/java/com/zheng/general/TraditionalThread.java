package com.zheng.general;

import org.junit.Test;

/**
 * Created by zhenglian on 2016/9/28.
 */
public class TraditionalThread {


    /**
     * 第一种方式，创建Thread子类
     */
    @Test
    public void createThread1() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("sub thread execute..." + Thread.currentThread().getName());
            }
        };
        thread.start();
        System.out.println("main thread execute..." + Thread.currentThread().getName());
    }
    
    @Test
    public void createThread2() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                System.out.println("sub thread execute..." + Thread.currentThread().getName());
            }
        });
        thread.start();

        System.out.println("main thread execute..." + Thread.currentThread().getName());
    }

    @Test
    public void testThread() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                System.out.println("runnable execute...");
            }
        }) {
            @Override
            public void run() {
                System.out.println("sub thread execute...");
            }
        };

        thread.start();
    }



}
