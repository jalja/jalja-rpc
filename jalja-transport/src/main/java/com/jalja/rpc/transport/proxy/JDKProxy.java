package com.jalja.rpc.transport.proxy;

import com.jalja.rpc.common.RemoteInvocation;

import com.jalja.rpc.transport.route.IRoute;
import com.jalja.rpc.transport.rpc.RpcProperties;

import java.lang.reflect.Proxy;

/**
 * @author XL
 * @title: JDKProxy
 * @projectName jalja-rpc
 * @date 2020/6/24 17:01
 * @description: JDK的动态代理
 */
public class JDKProxy  implements IProxy{
    @Override
    public  <T> T getProxy(ProxyModel model) {
        T t=(T) Proxy.newProxyInstance(model.getInterfaceClass().getClassLoader(),new Class[]{model.getInterfaceClass()},(proxy, method, args)->{
            RemoteInvocation invocation=new RemoteInvocation(model.getInterfaceClass().getName(),method.getName(),method.getParameterTypes(),args);
            RpcProperties rpcProperties=new RpcProperties();
            rpcProperties.setSerializableType(model.getSerializableType());
            String address=IRoute.getUrl(model);
            rpcProperties.setServerAddress(address);
            return model.getRpcServer().invokeProtocl(rpcProperties,invocation);
        } );
       return t;
    }
}
