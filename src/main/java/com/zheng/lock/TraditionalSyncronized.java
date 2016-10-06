package com.zheng.lock;

/**
 * Created by zhenglian on 2016/9/28.
 */
public class TraditionalSyncronized {
    final Output printer = new Output();

    public static void main(String[] args) {
        new TraditionalSyncronized().init();
    }

    private void init() {
        new Thread(new Runnable() {
            public void run() {
                while(true) {
                    printer.say("good good study");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                while(true) {
                    printer.say("day day up");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    
    class Output {
        public /*synchronized*/ void say(String msg) { //同步方法的方式
            synchronized (this) {
                for(char c : msg.toCharArray()) {
                    System.out.print(c);
                }
                System.out.println();
            }
        }
    }
    
}
