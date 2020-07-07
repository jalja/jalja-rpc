package com.jalja.rpc.transport.rpc.tomcat;

import com.jalja.rpc.common.RemoteInvocation;
import com.jalja.rpc.common.rpc.RpcInterfaceInstance;
import com.jalja.rpc.common.seria.SeriaSpi;
import com.jalja.rpc.transport.rpc.RpcProperties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

/**
 * @author XL
 * @title: DispatcherServlet
 * @projectName jalja-rpc
 * @date 2020/6/2420:50
 * @description: TODO
 */
public class DispatcherServlet extends HttpServlet {
    private RpcProperties rpcProperties;

    public DispatcherServlet(RpcProperties rpcProperties) {
        this.rpcProperties = rpcProperties;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        try{
            InputStream is = req.getInputStream();
            ObjectInputStream stream=new ObjectInputStream(is);
            RemoteInvocation invocation=(RemoteInvocation)stream.readObject();
            //拿到服务的名字
            String interfaceName = invocation.getInterfaceName();
            //从注册中心里面拿到接口的实现类
            Class interfaceImplClass = RpcInterfaceInstance.getClass(interfaceName);
            //获取类的方法
            Method method = interfaceImplClass.getMethod(invocation.getMethodName(),invocation.getParamtypes());
            //反射调用方法
            Object result = method.invoke(interfaceImplClass.newInstance(),invocation.getObjects());
            //把结果返回给调用者
            byte[] bytes= SeriaSpi.getIJaljaSerializable(Class.forName(rpcProperties.getSerializableType())).serialize(result);
            resp.getOutputStream().write(bytes);
            resp.getOutputStream().close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
