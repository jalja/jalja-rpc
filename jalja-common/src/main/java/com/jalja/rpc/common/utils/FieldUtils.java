package com.jalja.rpc.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XL
 * @title: FieldUtils
 * @projectName jalja-rpc
 * @date 2020/7/3 18:55
 * @description: 反射的工具类
 */
public class FieldUtils {
    public static <T> List<String> fileNames(Class<T> tClass){
        List<String> fileNames=new ArrayList<>();
        // 获得指定类的属性
        Field[]fields = tClass.getDeclaredFields();
        for(Field f:fields){
            fileNames.add(f.getName());
        }
        return fileNames;
    }
   public static <T> void setFieldValue(T t,String fieldName,Object value)  {
        try {
            Class<?> clazz =t.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(t, value);
        }catch (Exception e){
            e.printStackTrace();
        }
   }
}
