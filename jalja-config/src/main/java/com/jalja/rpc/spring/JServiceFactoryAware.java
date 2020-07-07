package com.jalja.rpc.spring;


import com.jalja.rpc.common.rpc.RpcInterfaceInstance;

import com.jalja.rpc.producer.JService;
import org.springframework.beans.BeansException;

import org.springframework.beans.factory.config.BeanPostProcessor;

import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

/**
 * @author XL
 * @title: JServiceFactoryAware
 * @projectName jalja-rpc
 * @date 2020/7/316:41
 * @description: TODO
 */
@Component
public class JServiceFactoryAware implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class  cz=bean.getClass();
        Annotation annotation=cz.getAnnotation(JService.class);
        if(annotation==null){
            return bean;
        }
        Class<?> interfaces[] = bean.getClass().getInterfaces();//获得Dog所实现的所有接口
        if(interfaces.length>1){
            throw new RuntimeException("不能多实现");
        }
        RpcInterfaceInstance.put(interfaces[0].getName(),bean.getClass());
        return bean;
    }
}
