package com.zheng.general;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhenglian on 2016/9/28.
 */
public class TraditionalTimer {

    private static int count = 0;

    public static void main(String[] args) {
        TraditionalTimer timer = new TraditionalTimer();
        timer.reduceTimer();
    }

    public void onceTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行一次...");
            }
        }, 1000);
    }

    public void moreTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行多次...");
            }
        }, 1000, 1000);
    }

    /**
     * 以2秒执行再4秒执行一次为一组，交替执行
     */
    public void reduceTimer() {
        new Timer().schedule(new MyTimerTask(), 2000);
    }

    class MyTimerTask extends TimerTask {
        public void run() {
            count = (count+1) % 2;
            System.out.println("execute...");
            new Timer().schedule(new MyTimerTask(), 2000+count*2000);
        }
    }
}
