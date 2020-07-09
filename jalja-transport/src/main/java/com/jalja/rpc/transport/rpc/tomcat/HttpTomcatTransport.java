package com.jalja.rpc.transport.rpc.tomcat;

import com.jalja.rpc.common.rpc.RemoteInvocation;
import com.jalja.rpc.common.seria.IJaljaSerializable;
import com.jalja.rpc.common.seria.SerializableSPI;
import com.jalja.rpc.common.utils.OkHttpUtils;
import com.jalja.rpc.transport.rpc.IRpcTransport;
import com.jalja.rpc.transport.rpc.RpcProperties;
import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;


/**
 * @author XL
 * @title: HttpTomcatServer
 * @projectName jalja-rpc
 * @date 2020/6/24 20:39
 * @description: TODO
 */
public class HttpTomcatTransport implements IRpcTransport {
    @Override
    public void start(RpcProperties rpcProperties)  {
        String [] address=rpcProperties.getServerAddress().split(":");
        Tomcat tomcat = new Tomcat();
        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");
        Connector connector = new Connector();
        connector.setPort(Integer.valueOf(address[1]));
        Engine engine = new StandardEngine();
        engine.setDefaultHost(address[0]);

        Host host = new StandardHost();
        host.setName(address[0]);
        //设置上下文
        String contextPath="/rpc";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        host.addChild(context);
        engine.addChild(host);

        service.setContainer(engine);
        service.addConnector(connector);

        //设置拦截servlet
        tomcat.addServlet(context,"dispather",new DispatcherServlet(rpcProperties));
        context.addServletMappingDecoded("/*","dispather");
        try {
            //启动tomcat
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Object invokeProtocl(RpcProperties rpcProperties, RemoteInvocation invocation) {
        try {
            String http="http://"+rpcProperties.getServerAddress()+"/rpc";
            IJaljaSerializable serializable= SerializableSPI.getIJaljaSerializable(Class.forName(rpcProperties.getSerializableType()));
            return OkHttpUtils.post(http,invocation,serializable);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
