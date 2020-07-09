package com.jalja.rpc.transport.rpc.tcp.result;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author XL
 * @title: NettyResponse
 * @projectName jalja-rpc
 * @date 2020/7/99:24
 * @description: TODO
 */
public class NettyResponse {
    private CountDownLatch latch;
    private Object result;
    public NettyResponse(CountDownLatch latch) {
        this.latch = latch;
    }
    public Object getResponse(){
        if(result!=null){
            return result;
        }
        try {
            latch.await();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
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
