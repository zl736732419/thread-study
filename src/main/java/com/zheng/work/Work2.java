package com.zheng.work;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有3个线程，主线程执行10次，子线程1执行20次，子线程2执行30次
 * 这样交替执行50次
 * Created by zhenglian on 2016/10/2.
 */
public class Work2 {
    private int count = 50;
    private PrintWork printer = new PrintWork();

    public static void main(String[] args) {
        new Work2().init();
    }

    public void init() {
        new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i <= count; i++) {
                    printer.sub1(i);
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i <= count; i++) {
                    printer.sub2(i);
                }
            }
        }).start();

        for (int i = 1; i <= count; i++) {
            printer.mainWork(i);
        }
    }

    class PrintWork {
        private int shouldSubRun = 0; //0表示主线程执行;1,2表示子线程执行
        private Lock lock = new ReentrantLock();
        private Condition mainRun = lock.newCondition();
        private Condition sub1Run = lock.newCondition();
        private Condition sub2Run = lock.newCondition();

        /**
         * 主线程任务
         */
        public void mainWork(int loop) {
            lock.lock();
            try {
                while(shouldSubRun != 0) {
                    try {
                        mainRun.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "..." + loop + "," + (i + 1));
                }

                shouldSubRun = 1;
                sub1Run.signal();
            }finally {
                lock.unlock();
            }

        }

        /**
         * 子线程任务
         */
        public void sub1(int loop) {
            lock.lock();
            try {
                while(shouldSubRun != 1) {
                    try {
                        sub1Run.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "..." + loop + "," + (i + 1));
                }
                shouldSubRun = 2;
                sub2Run.signal();
            } finally {
              lock.unlock();
            }

        }

        /**
         * 子线程任务
         */
        public void sub2(int loop) {
            lock.lock();
            try {
                while(shouldSubRun != 2) {
                    try {
                        sub2Run.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "..." + loop + "," + (i + 1));
                }
                shouldSubRun = 0;
                mainRun.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}
