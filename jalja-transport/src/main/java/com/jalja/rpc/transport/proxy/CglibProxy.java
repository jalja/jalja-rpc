package com.jalja.rpc.transport.proxy;


import com.jalja.rpc.common.rpc.RemoteInvocation;
import com.jalja.rpc.transport.route.IRoute;
import com.jalja.rpc.transport.rpc.RpcProperties;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author XL
 * @title: CgLibProxy
 * @projectName jalja-rpc
 * @date 2020/6/2418:23
 * @description: Cglib 动态代理
 */
public class CglibProxy implements IProxy {
    @Override
    public <T> T getProxy(ProxyModel model) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(model.getInterfaceClass());
        enhancer.setCallback(new  MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                RemoteInvocation invocation=new RemoteInvocation(model.getInterfaceClass().getName(),method.getName(),method.getParameterTypes(),args);
                String address= IRoute.getUrl(model);
                RpcProperties rpcProperties=new RpcProperties();
                rpcProperties.setSerializableType(model.getSerializableType());
                rpcProperties.setServerAddress(address);
                return model.getRpcServer().invokeProtocl(rpcProperties,invocation);
            }
        });
        return (T) enhancer.create();
    }

}
