package com.jalja.rpc.common.rpc;

import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XL
 * @title: RpcServerAddress
 * @projectName jalja-rpc
 * @date 2020/7/3 22:03
 * @description: TODO
 */
public class RpcServerAddress {

    private static Map<String,Set<String>> ipMap=new ConcurrentHashMap<>();

    public static void clear(){
        ipMap.clear();
    }

    /**
     * 查询服务器地址
     * @param cName
     * @return
     */
    public static List<String> getIps(String cName) {
        Set<String> sIp=ipMap.get(cName);
        if(CollectionUtils.isEmpty(sIp)){
            return null;
        }
        return new ArrayList<>(sIp);
    }

    /**
     * 根据接口名添加 ，服务器IP
     * @param cName
     * @param address
     */
    public static void setIps(String cName,List<String> address) {
        Set<String> ips=new HashSet<>();
        if(address!=null && address.size()>0){
            address.stream().forEach((ip)->{
                ips.add(ip);
            });
            ipMap.put(cName,ips);
        }
    }
}
