package com.jalja.rpc.event;

import com.jalja.rpc.config.RpcConfigProperties;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationEvent;

/**
 * @author XL
 * @title: RpcPropertiesInitEvent
 * @projectName jalja-rpc
 * @date 2020/7/48:54
 * @description: TODO
 */
public class RpcPropertiesInitEvent extends ApplicationEvent {
    private RpcConfigProperties properties;
    private BeanFactory beanFactory;

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public RpcPropertiesInitEvent(BeanFactory beanFactory, RpcConfigProperties properties) {
        super(properties);
        this.properties=properties;
        this.beanFactory=beanFactory;
    }
}
