package com.jalja.rpc.producer;

import com.jalja.rpc.common.utils.NetUtils;
import com.jalja.rpc.config.RpcConfigProperties;
import com.jalja.rpc.transport.rpc.IRpcTransport;
import com.jalja.rpc.transport.rpc.RpcProperties;
import com.jalja.rpc.transport.rpc.RpcTransportSPI;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * @author XL
 * @title: Producer
 * @projectName jalja-rpc
 * @date 2020/7/3 16:12
 * @description: 启动服务器
 */
@Component
public class JaljaRpcProducer implements ApplicationListener<ContextRefreshedEvent> {
    private ApplicationContext context;
    private RpcConfigProperties properties;
    private Logger logger= LoggerFactory.getLogger(JaljaRpcProducer.class);
    public void start() throws Exception {
        if(StringUtils.equals(properties.getApplicationType(),"2")){
            return;
        }
        if(StringUtils.isEmpty(properties.getServerAddress())){
            throw new RuntimeException("ServerAddress is  null");
        }
        boolean flag=NetUtils.isPortUsing(properties.getServerAddress(),Integer.valueOf(properties.getServerPort()));
        if(flag){
            throw new RuntimeException("端口被占用："+properties.getServerPort());
        }
        IRpcTransport protocl = RpcTransportSPI.getPRC(Class.forName(properties.getProtocolType()));
        RpcProperties rpcProperties=new RpcProperties();
        rpcProperties.setProtocolType(properties.getProtocolType());
        rpcProperties.setServerAddress(properties.getServerAddress()+":"+properties.getServerPort());
        rpcProperties.setSerializableType(properties.getSerializableType());
        protocl.start(rpcProperties);
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            this.context=event.getApplicationContext();
            properties=(RpcConfigProperties)context.getBean(RpcConfigProperties.class.getName());
            start();
        }catch (Exception e){
            logger.error("启动服务器",e);
        }
    }
}
