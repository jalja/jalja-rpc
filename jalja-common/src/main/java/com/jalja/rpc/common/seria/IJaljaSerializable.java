package com.jalja.rpc.common.seria;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author XL
 * @title: IJaljaSerializable
 * @projectName jalja-rpc
 * @date 2020/6/24 16:18
 * @description: 序列化抽象
 */
public interface IJaljaSerializable {
    /**
     * 序列化
     * @param obj
     * @return
     */
    public  byte[] serialize(Object obj) throws IOException;

    /**
     * 反序列化
     * @param data
     * @param cls
     * @param <T>
     * @return
     */
    public  <T> T deserialize(byte[] data, Type cls) throws IOException, ClassNotFoundException;
}
