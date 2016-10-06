package com.zheng.work;

/**
 * 该程序主要是为了达到子线程循环10次，然后主线程循环100次
 * 这样交替执行50次的效果
 * <p>
 * Created by zhenglian on 2016/9/29.
 */
public class Work1 {
    private PrintWork printer = new PrintWork();
    private int count = 50; //记录循环50次

    public static void main(String[] args) {
        new Work1().init();
    }

    public void init() {
        new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i <= count; i++) {
                    printer.doSubWork(i);
                }
            }
        }).start();

        for (int i = 1; i <= count; i++) {
            printer.doMainWork(i);
        }
    }

    class PrintWork {
        private boolean shouldSubRun = true;
        /**
         * 主线程任务
         */
        public synchronized void doMainWork(int loop) {

            while(shouldSubRun) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + "..." + loop + "," + (i + 1));
            }

            shouldSubRun = true;
            this.notify();

        }

        /**
         * 子线程任务
         */
        public synchronized void doSubWork(int loop) {
            while(!shouldSubRun) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "..." + loop + "," + (i + 1));
            }
            shouldSubRun = false;
            this.notify();

        }
    }

}
