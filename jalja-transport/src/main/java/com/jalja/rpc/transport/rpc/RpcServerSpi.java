package com.jalja.rpc.transport.rpc;

import java.util.ServiceLoader;

/**
 * @author XL
 * @title: RpcServerSpi
 * @projectName jalja-rpc
 * @date 2020/6/24 22:41
 * @description: TODO
 */
public class RpcServerSpi {
    public static IRpcServer getPRC(Class cz){
        ServiceLoader<IRpcServer> shouts = ServiceLoader.load(IRpcServer.class);
        for (IRpcServer s : shouts) {
            if(s.getClass().getName().equals(cz.getName())){
                return s;
            }
        }
        return null;
    }

}
