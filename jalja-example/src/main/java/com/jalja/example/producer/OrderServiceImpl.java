package com.jalja.example.producer;


import com.jalja.example.service.OrderService;
import com.jalja.rpc.producer.JService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 * @author XL
 * @title: OrderServiceImpl
 * @projectName jalja-rpc
 * @date 2020/7/315:07
 * @description: TODO
 */
@JService
public class OrderServiceImpl implements  OrderService {
    private Logger logger= LoggerFactory.getLogger(OrderServiceImpl.class);
    @Override
    public void create(int id) {
        logger.info(new Date()+"创建订单"+id);
    }
}
