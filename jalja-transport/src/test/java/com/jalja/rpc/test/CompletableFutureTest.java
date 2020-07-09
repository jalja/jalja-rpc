package com.jalja.rpc.test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * @author XL
 * @title: CompletableFutureTest
 * @projectName jalja-rpc
 * @date 2020/7/917:09
 * @description: TODO
 */
public class CompletableFutureTest {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello world";
        });
        System.out.println(future.get());  //阻塞的获取结果  ''helllo world"


    }
}
