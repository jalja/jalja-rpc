package com.jalja.example.producer;



import com.jalja.rpc.config.EnableJaljaRpc;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @author XL
 * @title: SpringContainer
 * @projectName jalja-rpc
 * @date 2020/7/316:49
 * @description: TODO
 */
@Configurable

@ComponentScan(basePackages={"com.jalja.example.producer"})
@PropertySource(value = {"classpath:spring.properties"})
@Import(EnableJaljaRpc.class)
public class ProducerContainer {

}
