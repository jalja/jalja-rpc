package com.jalja.rpc.transport.rpc;

import java.util.ServiceLoader;

/**
 * @author XL
 * @title: RpcServerSpi
 * @projectName jalja-rpc
 * @date 2020/6/24 22:41
 * @description: TODO
 */
public class RpcTransportSPI {
    public static IRpcTransport getPRC(Class cz){
        ServiceLoader<IRpcTransport> shouts = ServiceLoader.load(IRpcTransport.class);
        for (IRpcTransport s : shouts) {
            if(s.getClass().getName().equals(cz.getName())){
                return s;
            }
        }
        return null;
    }

}
