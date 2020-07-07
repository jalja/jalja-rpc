package com.jalja.rpc.common.utils;

import net.sf.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XL
 * @title: BeanCopy
 * @projectName jalja-utils
 * @date 2020/5/28 10:16
 * @description: Cglib属性的Cpoy
 */
public class BeanCopyUtils {
    private static final Map<String, BeanCopier> BEAN_COPIERS = new HashMap<String, BeanCopier>();
    /**
     * 屬性copy
     * @param target 目标
     * @param source 源
     * @param <T>
     */
    public static <T,E> void beanCopy(T target,E source){
        String key = genKey(source.getClass(), target.getClass());
        BeanCopier beanCopier=null;
        try {
            beanCopier=BEAN_COPIERS.get(key);
            if(beanCopier==null){
                beanCopier = BeanCopier.create(target.getClass(),target.getClass(),false);
                BEAN_COPIERS.put(key,beanCopier);
            }
            beanCopier.copy(source,target,null);
        }catch (Exception e){
            throw new RuntimeException("cglib BeanCopier复制对象属性出错",e);
        }
    }

    private static String genKey(Class<?> srcClazz, Class<?> destClazz) {
        return srcClazz.getName() + destClazz.getName();
    }

}
