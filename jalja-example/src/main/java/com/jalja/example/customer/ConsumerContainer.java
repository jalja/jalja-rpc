package com.jalja.example.customer;



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

@ComponentScan(basePackages={"com.jalja.example.customer"})
@PropertySource(value = {"classpath:consumer.properties","log4j.properties"})
@Import(EnableJaljaRpc.class)
public class ConsumerContainer {
}
