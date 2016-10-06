package com.zheng.util;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by zhenglian on 2016/10/2.
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        new CyclicBarrierTest().init();
    }

    private void init() {
        final CyclicBarrier pers = new CyclicBarrier(3); //3个线程
        for(int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(new Random().nextInt(3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int num = 3 - pers.getNumberWaiting()-1;
                    System.out.println(Thread.currentThread().getName() + "到达集合点1，还需要等待" + num + "个用户才可执行!");
                    try {
                        pers.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(num == 0) {
                        System.out.println("用户到齐，前往下一地点...");
                    }

                    try {
                        Thread.sleep(new Random().nextInt(3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    num = 3 - pers.getNumberWaiting()-1;
                    System.out.println(Thread.currentThread().getName() + "到达集合点2，还需要等待" + num + "个用户才可执行!");
                    try {
                        pers.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(num == 0) {
                        System.out.println("用户到齐，前往下一地点...");
                    }

                    try {
                        Thread.sleep(new Random().nextInt(3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    num = 3 - pers.getNumberWaiting()-1;
                    System.out.println(Thread.currentThread().getName() + "到达终点，还需要等待" + num + "个用户才可执行!");
                    try {
                        pers.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(num == 0) {
                        System.out.println("用户到齐，完成任务...");
                    }


                }
            }).start();
        }

    }

}
