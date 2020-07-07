package com.jalja.example.customer;

import com.jalja.example.service.HelloService;
import com.jalja.example.service.OrderService;
import com.jalja.rpc.consumer.JReference;
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
    public  void hello(String name){
       System.out.println(helloService.sayHello(name));
    }
    public void  create(int id){
        orderService.create(id);
    }
}
