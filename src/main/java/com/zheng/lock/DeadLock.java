package com.zheng.lock;

/**
 * Created by zhenglian on 2016/9/28.
 */
public class DeadLock {
    private Object lock1 = new Object();
    private Object lock2 = new Object();
    final private Output printer = new Output();

    public static void main(String[] args) {
        new DeadLock().init();
    }

    private void init() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    synchronized (lock1) {
                        System.out.println(Thread.currentThread().getName() + "获取了锁lock1...");
                        try {
                            Thread.sleep(50);
                            synchronized (lock2) {
                                System.out.println(Thread.currentThread().getName()+"获取了锁lock2...");

                                printer.say(Thread.currentThread().getName() + "执行...");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    synchronized (lock2) {
                        System.out.println(Thread.currentThread().getName() + "获取了锁lock2...");
                        try {
                            Thread.sleep(50);
                            synchronized (lock1) {
                                System.out.println(Thread.currentThread().getName()+"获取了锁lock1...");

                                printer.say(Thread.currentThread().getName() + "执行...");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();


    }

    class Output {
        public synchronized void say(String msg) {
            for (char c : msg.toCharArray()) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
}
