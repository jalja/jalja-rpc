package com.jalja.example.producer;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author XL
 * @title: SpringProducer
 * @projectName jalja-rpc
 * @date 2020/7/316:49
 * @description: TODO
 */
public class SpringProducer {
    public static void main( String[] args ) throws InterruptedException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ProducerContainer.class);
    }
}
