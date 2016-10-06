package com.zheng.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhenglian on 2016/10/1.
 */
public class LockTest {
    public static void main(String[] args) {
        new LockTest().init();
    }

    private void init() {
        final Output out = new Output();
        new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    out.print("xiaozhang");
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    out.print("hello");
                }
            }
        }).start();
    }


    class Output {

        private Lock lock = new ReentrantLock();

        public void print(String msg) {

            lock.lock();
            try {
                for(char c : msg.toCharArray()) {
                    System.out.print(c);
                }
                System.out.println();
            } finally {
                lock.unlock();
            }


        }
    }
    
}
