package com.jalja.rpc.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.lang.annotation.*;

/**
 * @author XL
 * @title: JReference
 * @projectName jalja-rpc
 * @date 2020/7/323:11
 * @description: TODO
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Lazy
public @interface JReference {
}
