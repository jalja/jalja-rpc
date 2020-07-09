package com.jalja.example.customer;

import com.alibaba.fastjson.JSON;
import com.jalja.example.producer.HelloServiceImpl;
import com.jalja.example.service.HelloService;
import com.jalja.example.service.OrderService;
import com.jalja.rpc.consumer.JReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author XL
 * @title: HelloClient
 * @projectName jalja-rpc
 * @date 2020/7/323:30
 * @description: TODO
 */
@Service
public class HelloClient {
    @JReference
    private HelloService helloService;
    @JReference
    private OrderService orderService;
    private Logger logger= LoggerFactory.getLogger(HelloClient.class);
    public  void hello(String name){
        logger.info("hello:{}", JSON.toJSON(helloService.sayHello(name)));
    }
    public void  create(int id){
        orderService.create(id);
    }
}
