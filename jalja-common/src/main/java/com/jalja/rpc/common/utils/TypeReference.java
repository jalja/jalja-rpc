package com.jalja.rpc.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 泛型类型声明
 *
 * @param <T>
 * @author jinxiao.wu
 * @createtime 2019-08-27
 */
public class TypeReference<T> implements Type {
    static ConcurrentMap<Type, Type> classTypeCache = new ConcurrentHashMap<>(16, 0.75f, 1);

    protected final Type type;

    protected TypeReference() {
        Type superClass = getClass().getGenericSuperclass();

        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];

        Type cachedType = classTypeCache.get(type);
        if (cachedType == null) {
            classTypeCache.putIfAbsent(type, type);
            cachedType = classTypeCache.get(type);
        }

        this.type = cachedType;
    }


    /**
     * Gets underlying {@code Type} instance.
     */
    public Type getType() {
        return type;
    }
}