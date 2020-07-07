package com.jalja.rpc.spring;

import com.jalja.rpc.common.utils.BeanCopyUtils;
import com.jalja.rpc.common.utils.FieldUtils;
import com.jalja.rpc.common.utils.NetUtils;
import com.jalja.rpc.config.ConsumerProperties;
import com.jalja.rpc.config.RpcConfigProperties;

import com.jalja.rpc.event.ServerStartEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author XL
 * @title: ConfigurationClassPostProcessorTest
 * @projectName jalja-rpc
 * @date 2020/7/49:40
 * @description: TODO
 */
@Component
public class PropertiesEnvironmentAware extends ConfigurationClassPostProcessor {
    private RpcConfigProperties properties = new RpcConfigProperties();
    private ConsumerProperties consumerProperties=new ConsumerProperties();
    @Override
    public void setEnvironment(Environment environment) {
        List<String> files= FieldUtils.fileNames(RpcConfigProperties.class);
        files.forEach((name)->{
            String key=properties.LEGACY_PROPERTIES.get(name);
            if(key!=null && !key.equals("")){
                Object v=environment.getProperty(key);
                if(v!=null){
                    FieldUtils.setFieldValue(properties,name,v);
                }
            }
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory)beanFactory;
        BeanUtils.copyProperties(properties,consumerProperties);
        properties.setServerAddress(NetUtils.getCurrentIp().getHostAddress());
        listableBeanFactory.registerSingleton(RpcConfigProperties.class.getName(),properties);
        listableBeanFactory.registerSingleton(ConsumerProperties.class.getName(),consumerProperties);
        super.postProcessBeanFactory(beanFactory);
    }
}
