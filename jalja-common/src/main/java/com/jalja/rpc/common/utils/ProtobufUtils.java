package com.jalja.rpc.common.utils;


import io.protostuff.*;
import io.protostuff.runtime.RuntimeSchema;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 使用 Protostuff 实现序列化
 *
 * @author jinxiao.wu
 * @createtime 2018-08-27
 */
public final class ProtobufUtils {

    /**
     * 序列化
     *
     * @param obj 数据
     * @return
     */
    public static byte[] serialize(Object obj) throws IllegalArgumentException {
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema schema = getSchema(obj);
            if (null != schema) {
                return ProtobufIOUtil.toByteArray(obj, schema, buffer);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("protostuff 序列化失败", e);
        } finally {
            buffer.clear();
        }
    }

    /**
     * 反序列化（字节数组 -> 对象）
     */
    public static <T> T deserialize(byte[] data, Type cls) throws IllegalArgumentException {
        try {
            Schema<T> schema = getSchema(cls instanceof TypeReference<?> ? ((TypeReference) cls).getType() : cls);
            T message = schema.newMessage();
            ProtobufIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalArgumentException("protostuff 反序列化失败", e);
        }
    }

    /**
     * 反序列化类型自动推算,识别Collection和map
     *
     * @param type
     * @return
     */
    private static Schema getSchema(Type type) {
        if (type instanceof Class<?>) {
            return RuntimeSchema.getSchema((Class) type);
        } else if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class<?>) {
                Class<?> clazz = (Class) rawType;
                if (Collection.class.isAssignableFrom(clazz)) {
                    return new MessageCollectionSchema(RuntimeSchema.getSchema((Class) ((ParameterizedType) type).getActualTypeArguments()[0]));
                } else if (Map.class.isAssignableFrom(clazz)) {
                    return new MessageMapSchema(RuntimeSchema.getSchema((Class) ((ParameterizedType) type).getActualTypeArguments()[0]),
                            RuntimeSchema.getSchema((Class) ((ParameterizedType) type).getActualTypeArguments()[1]));
                } else {
                    return RuntimeSchema.getSchema((Class) rawType);
                }
            } else {
                return getSchema(rawType);
            }
        } else {
            return null;
        }
    }

    /**
     * 获取数据结构，包装Collection和map
     *
     * @param obj
     * @return
     */
    private static Schema getSchema(Object obj) {
        if (obj instanceof Map) {
            Iterator<Map.Entry<?, ?>> it = ((Map) obj).entrySet().iterator();
            if (it.hasNext()) {
                Map.Entry<?, ?> item = it.next();
                return new MessageMapSchema(RuntimeSchema.getSchema(item.getKey().getClass()),
                        RuntimeSchema.getSchema(item.getValue().getClass()));
            } else {
                return null;
            }
        } else if (obj instanceof Collection) {
            Iterator<?> it = ((Collection) obj).iterator();
            if (it.hasNext()) {
                return new MessageCollectionSchema(RuntimeSchema.getSchema(it.next().getClass()));
            } else {
                return null;
            }
        } else {
            return RuntimeSchema.getSchema(obj.getClass());
        }
    }


}