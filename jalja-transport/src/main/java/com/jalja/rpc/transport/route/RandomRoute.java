package com.jalja.rpc.transport.route;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;


import java.util.List;

/**
 * @author XL
 * @title: RandomRoute
 * @projectName jalja-rpc
 * @date 2020/7/410:10
 * @description: TODO
 */
public class RandomRoute implements IRoute {
    @Override
    public String getIP(List<String> ips) {
       if(CollectionUtils.isEmpty(ips)){
           throw new RuntimeException("无可用服务");
       }
       int ru= RandomUtils.nextInt(0,ips.size());
       return ips.get(ru);
    }
}
