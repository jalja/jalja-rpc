package com.jalja.rpc.test;

import java.util.concurrent.CountDownLatch;

/**
 * @author XL
 * @title: CountDownLatchDemo
 * @projectName jalja-rpc
 * @date 2020/7/99:50
 * @description: TODO
 */
public class CountDownLatchDemo {
    private CountDownLatch latch;
    private Object result;
    public CountDownLatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }
    public Object get() throws InterruptedException {
        latch.await();
        return result;
    }
    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
