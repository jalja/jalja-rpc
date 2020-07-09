package com.jalja.rpc.test;

import com.jalja.rpc.common.Charsets;

/**
 * @author XL
 * @title: Maintest
 * @projectName jalja-rpc
 * @date 2020/7/815:48
 * @description: TODO
 */
public class Maintest {
    public static void main(String[] args) {
        long time=System.nanoTime();
        System.out.println(time);
        time=System.nanoTime();
        System.out.println(String.valueOf(time).getBytes(Charsets.US_ASCII).length);
    }
}
