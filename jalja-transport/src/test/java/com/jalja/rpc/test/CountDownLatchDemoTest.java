package com.jalja.rpc.test;

import java.util.concurrent.CountDownLatch;

/**
 * @author XL
 * @title: CountDownLatchDemoTest
 * @projectName jalja-rpc
 * @date 2020/7/99:51
 * @description: TODO
 */
public class CountDownLatchDemoTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo countDownLatchDemo=new CountDownLatchDemo(new CountDownLatch(1));
        new Thread(()->{
            countDownLatchDemo.setResult(11);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatchDemo.getLatch().countDown();

        }).start();
        System.out.println(countDownLatchDemo.get());


    }
}
