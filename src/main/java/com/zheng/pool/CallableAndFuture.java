package com.zheng.pool;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by zhenglian on 2016/10/1.
 */
public class CallableAndFuture {

    public static void main(String[] args) throws Exception {
//        CallableAndFuture().executeFuture();
//
//        for(int i = 0; i < 10; i++) {
//            System.out.println(Thread.currentThread().getName() + "--" + i);
//        }

        new CallableAndFuture().CompletionServiceTest();
    }

    private void executeFuture() {
        new Thread(new Runnable() {
            public void run() {
                ExecutorService service = Executors.newSingleThreadExecutor();
                Future<String> future = service.submit(new Callable<String>() {
                    public String call() throws Exception {
                        Thread.sleep(3000);
                        return "hello";
                    }
                });

                System.out.println(Thread.currentThread().getName() + "得到的结果是: ");
                try {
                    System.out.println(future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                System.out.println("执行完毕...");
            }
        }).start();
    }

    public void CompletionServiceTest() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletionService<Integer> service = new ExecutorCompletionService(executor);
        for(int i = 0; i < 10; i++) {
            final int task = i;
            service.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    Thread.sleep(new Random().nextInt(5000));
                    return task;
                }
            });
        }

        System.out.println("获取返回结果");
        for(int i = 0; i < 10; i++) {
            System.out.println(service.take().get());
        }
        
        System.out.println("任务处理完成...");
    }

}
