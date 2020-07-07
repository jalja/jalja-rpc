package com.jalja.example.producer;


import com.jalja.example.service.HelloService;
import com.jalja.example.service.Person;
import com.jalja.rpc.producer.JService;

/**
 * @author XL
 * @title: HelloServiceImpl
 * @projectName jalja-rpc
 * @date 2020/6/24 21:08
 * @description: TODO
 */
@JService
public class HelloServiceImpl implements HelloService {
    @Override
    public Person sayHello(String name) {
        return  new Person(1,name+"中国");
    }
}
