package com.zheng.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhenglian on 2016/10/1.
 */
public class ConditionTest {

    private WorkeTask worker = new WorkeTask();

    public static void main(String[] args) {
        new ConditionTest().init();
    }

    private void init() {
        new Thread(new Runnable() {
            public void run() {
                for(int i = 0; i < 50; i++) {
                    worker.subWork(i);
                }
            }
        }).start();

        for(int i = 0; i < 50; i++) {
            worker.mainWork(i);
        }

    }

    class WorkeTask {
        private ReentrantLock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();
        private boolean shouldSub = true;

        private void subWork(Integer count) {
            lock.lock();
            try {
                while(!shouldSub) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + " loop of " + i + " task " + count);
                }

                shouldSub = false;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }

        private void mainWork(Integer count) {
            lock.lock();
            try {
                while(shouldSub) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " loop of " + i + " task " + count);
                }

                shouldSub = true;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }

    }

}
