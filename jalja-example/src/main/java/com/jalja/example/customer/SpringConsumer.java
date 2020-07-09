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
        HelloClient client=ctx.getBean(HelloClient.class);
        for(int i=0;i<Integer.MAX_VALUE;i++){
            int finalI = i;
            Thread.sleep(1000);
            for(int j=0;j<5;j++){
                new Thread(()->{
                    client.hello(finalI +" hello=>"+finalI);
                    client.create(finalI);
                }).start();
            }

        }
    }
}
