package com.jalja.rpc.transport.rpc.tcp.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.*;



/**
 * @author XL
 * @title: NettyResponseResult
 * @projectName jalja-rpc
 * @date 2020/7/815:17
 * @description: TODO
 */
public class NettyResponseResult {
   private static Map<String, NettyResponse> requestMap=new ConcurrentHashMap<>();
   private static Logger logger= LoggerFactory.getLogger(NettyResponseResult.class);
   public static void set(String key,NettyResponse v)  {
       requestMap.put(key,v);
   }
    public static NettyResponse getResponse(String key)  {
        return requestMap.get(key);
    }
    public static void remove(String key){
       if (requestMap.containsKey(key)){
           requestMap.remove(key);
       }
    }

    public static Object getResult(String key)  {
       try {
           CompletableFuture<Object> future = CompletableFuture.supplyAsync(()->{
               logger.info("getResult:{}",new Date()+Thread.currentThread().getName());
               NettyResponse response=requestMap.get(key);
               Object result= response.getResponse();
               requestMap.remove(key);
               return result;
           });
           return future.get();
       }catch (Exception e){
           logger.error("getResult:",e);
           return null;
       }
    }
}
