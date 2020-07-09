package com.jalja.rpc.transport.rpc.tcp.result;


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
       NettyResponse response=requestMap.get(key);
       Object result= response.getResponse();
       return result;
    }
}
