package com.zheng.util;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhenglian on 2016/10/2.
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        new CountDownLatchTest().init();
    }

    private void init() {
        final CountDownLatch teacher = new CountDownLatch(1);
        final CountDownLatch students = new CountDownLatch(3);

        for(int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "等待开始答卷通知...");
                    try {
                        teacher.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "开始答题");
                    try {
                        Thread.sleep(new Random().nextInt(3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "完成答题，交卷...");
                    students.countDown();
                }
            }).start();
        }

        System.out.println("准备开始考试...");
        try {
            Thread.sleep(new Random().nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("老师说开始答卷...");
        System.out.println("老师等待学生完成考试...");
        teacher.countDown();

        try {
            students.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("考试完毕...");


    }
}
