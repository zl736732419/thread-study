package com.zheng.util;

import java.util.concurrent.Semaphore;

/**
 * Created by zhenglian on 2016/10/2.
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        new SemaphoreTest().init();
    }

    private void init() {

        final Semaphore pers = new Semaphore(3);

        for(int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        pers.acquire();
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName()+"获取许可，当前还剩" +
                            pers.availablePermits() + "个许可");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName()
                            +"执行完毕，释放锁，当前还剩"+pers.availablePermits());
                    pers.release();
                }
            }).start();
        }
    }

}
