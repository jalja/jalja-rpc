package com.jalja.rpc.producer;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author XL
 * @title: JService
 * @projectName jalja-rpc
 * @date 2020/7/1 17:07
 * @description: 注入到 service实现类上
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface JService {

}
