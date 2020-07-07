package com.jalja.rpc.common.rpc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XL
 * @title: RpcInterfaceInstance
 * @projectName jalja-rpc
 * @date 2020/7/3 16:32
 * @description: TODO
 */
public class RpcInterfaceInstance {
    private static Map<String,Class> classMap=new ConcurrentHashMap<>();

    public static Map<String,Class> getClassMap(){
        return classMap;
    }
    /**
     * 存入映射关系
     * @param key
     * @param t
     */
    public static void put(String key,Class t){
        classMap.put(key,t);
    }
    /**
     * 根据key获取 class 类型
     * @param key
     * @param <T>
     * @return
     */
    public static <T> Class<T> getClass(String key)  {
        try {
            return classMap.get(key);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据key 获取 实例类型
     * @param key
     * @param <T>
     * @return
     */
    public static  <T> T getBean(String key)  {
        try {
            return (T) classMap.get(key).newInstance();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
