package com.jalja.example.customer;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author XL
 * @title: SpringProducer
 * @projectName jalja-rpc
 * @date 2020/7/316:49
 * @description: TODO
 */
public class SpringConsumer {
    public static void main( String[] args ) throws InterruptedException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ConsumerContainer.class);
        for(int i=0;i<1000;i++){
            final int a=i;

            new Thread(()->{
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HelloClient client=ctx.getBean(HelloClient.class);
                client.hello("I"+a);
                client.create(a);
            }).start();
        }
    }
}
