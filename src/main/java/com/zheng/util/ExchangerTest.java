package com.zheng.util;

import java.util.Random;
import java.util.concurrent.Exchanger;

/**
 * Created by zhenglian on 2016/10/2.
 */
public class ExchangerTest {

    public static void main(String[] args) {
        new ExchangerTest().init();
    }

    private void init() {
        final Exchanger<String> exchanger = new Exchanger();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(new Random().nextInt(3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "到达场所,准备交换");
                String money = "5w";
                String value = null;
                try {
                    value = exchanger.exchange(money);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                System.out.println(Thread.currentThread().getName() + "交换成功，得到" + value);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(new Random().nextInt(3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "到达场所,准备交换");
                String money = "彩票";
                String value = null;
                try {
                    value = exchanger.exchange(money);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "交换成功，得到" + value);
            }
        }).start();


    }

}
