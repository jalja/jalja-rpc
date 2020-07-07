package com.jalja.rpc.spring;

import com.jalja.rpc.common.rpc.RpcInterfaceInstance;
import com.jalja.rpc.common.utils.NetUtils;
import com.jalja.rpc.common.utils.ZKCuratorUtils;
import com.jalja.rpc.config.RpcConfigProperties;
import com.jalja.rpc.register.IRegister;
import com.jalja.rpc.register.RegisterSpi;
import com.jalja.rpc.register.model.RegisterModelDTO;
import com.jalja.rpc.register.zk.ZookeeperRegister;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;


import java.util.Map;

/**
 * @author XL
 * @title: RegisterFactoryAware
 * @projectName jalja-rpc
 * @date 2020/7/3 17:17
 * @description: 服务器加入注册中心
 */
@Component
public class RegisterFactoryAware implements BeanFactoryAware {
    private Logger logger= LoggerFactory.getLogger(RegisterFactoryAware.class);
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        RpcConfigProperties properties=(RpcConfigProperties)beanFactory.getBean(RpcConfigProperties.class.getName());
        if(StringUtils.equals(properties.getProtocolType(),"2")){
            return;
        }
        Map<String,Class> classMap=RpcInterfaceInstance.getClassMap();
        if(classMap==null || classMap.size()<=0){
            return;
        }
        if(StringUtils.isEmpty(properties.getServerAddress())){
            throw new RuntimeException("ServerAddress is  null");
        }
        try {
            IRegister register= RegisterSpi.getRegister(Class.forName(properties.getRegistryType()));
            for(Map.Entry<String,Class> clMap:classMap.entrySet()){
                RegisterModelDTO modelDTO=new RegisterModelDTO();
                modelDTO.setIp(properties.getServerAddress());
                modelDTO.setClassName(clMap.getKey());
                if(register instanceof ZookeeperRegister){
                    ZookeeperRegister zookeeperRegister=(ZookeeperRegister) register;
                    ZKCuratorUtils curatorClient=new ZKCuratorUtils(properties.getRegistryAddress(),properties.getRootPath());
                    zookeeperRegister.setCuratorClient(curatorClient);
                }
                register.register(modelDTO);
                register.flush();
            }
        }catch (Exception e){
            logger.error("服务器加入注册中心:",e);
        }
    }
}
