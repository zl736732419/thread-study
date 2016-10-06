package com.zheng.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * Created by zhenglian on 2016/10/1.
 */
public class ThreadPool {
    public static void main(String[] args) {
//        new ThreadPool().createThreadPool();
        new ThreadPool().scheduleThreadPool();

    }

    private static void createThreadPool() {
        //        ExecutorService service = Executors.newFixedThreadPool(3);
//        ExecutorService service = Executors.newCachedThreadPool();
        ExecutorService service = Executors.newSingleThreadExecutor();
        for(int i = 0;  i < 10; i++) {
            final int task = i;
            service.execute(new Runnable() {
                public void run() {
                    for(int j = 0; j < 10; j++) {
                        try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " loop of " + j + " task " + task);
                }
                }
            });
        }

        service.shutdown();
    }

    public void scheduleThreadPool() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
        service.schedule(new Runnable() {
            public void run() {
                System.out.println("bloming!");
            }
        }, 2, TimeUnit.SECONDS);


        service.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("bloming!");
            }
        }, 6, 2, TimeUnit.SECONDS);
    }

}
