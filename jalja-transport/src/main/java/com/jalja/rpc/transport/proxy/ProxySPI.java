package com.jalja.rpc.transport.proxy;

import java.util.ServiceLoader;

/**
 * @author XL
 * @title: ProxySPI
 * @projectName jalja-rpc
 * @date 2020/6/24 22:23
 * @description: Proxy 的SPI
 */
public class ProxySPI {
    public static IProxy getIProxy(Class cz){
        ServiceLoader<IProxy> shouts = ServiceLoader.load(IProxy.class);
        for (IProxy s : shouts) {
            if(s.getClass().getName().equals(cz.getName())){
                return s;
            }
        }
        return null;
    }
}
