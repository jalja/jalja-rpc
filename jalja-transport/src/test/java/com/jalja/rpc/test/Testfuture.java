package com.jalja.rpc.test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author XL
 * @title: Testfuture
 * @projectName jalja-rpc
 * @date 2020/7/99:29
 * @description: TODO
 */
public class Testfuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //第一种方式
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(10000);
                return new Random().nextInt(10);
            }
        });
        new Thread(task).start();
        int f=task.get();
        System.out.println(f);
    }
}
class Task{
    private  Object futureTask;

    public Task(FutureTask<Integer> futureTask) {
        this.futureTask = futureTask;
    }
    public void get() throws ExecutionException, InterruptedException {
        //第一种方式
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(10000);
                return new Random().nextInt(10);
            }
        });
        new Thread(task).start();
        int f=task.get();
    }
}
