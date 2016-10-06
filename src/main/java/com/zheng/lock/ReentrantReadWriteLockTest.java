package com.zheng.lock;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试
 * Created by zhenglian on 2016/10/1.
 */
public class ReentrantReadWriteLockTest {
    
    public static void main(String[] args) {
        new ReentrantReadWriteLockTest().init();
    }

    private void init() {

        final MsgCenter center = new MsgCenter();
        for(int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                public void run() {
                    while(true) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            center.read();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            new Thread(new Runnable() {
                public void run() {
                    Integer data = null;
                    while(true) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        data = new Random().nextInt(5000);
                        center.write(data);
                    }
                }
            }).start();
        }
    }

    class MsgCenter {
        private Integer data = null;
        private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        public Integer read() throws Exception {
            lock.readLock().lock();

            try {
                System.out.println(Thread.currentThread().getName()+" ready to read data.");
                System.out.println(Thread.currentThread().getName()+" read data: "+data);
            } finally {
                lock.readLock().unlock();
            }

            return data;
        }

        public void write(Integer data) {
            lock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName()+" ready to write data.");
                this.data = data;
                System.out.println(Thread.currentThread().getName()+" write data: " + data);
            }finally {
                lock.writeLock().unlock();
            }
        }
    }

}
