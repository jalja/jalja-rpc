package com.jalja.rpc.common.seria;

import com.jalja.rpc.common.utils.ProtobufUtils;

import java.lang.reflect.Type;

/**
 * @author XL
 * @title: ProtobufSeria
 * @projectName jalja-rpc
 * @date 2020/6/24 16:21
 * @description:  Protobuf 的序列化方式
 */
public class ProtobufSeria implements IJaljaSerializable {
    @Override
    public byte[] serialize(Object obj) {
        return ProtobufUtils.serialize(obj);
    }

    @Override
    public <T> T deserialize(byte[] data, Type cls) {
        return ProtobufUtils.deserialize(data,cls);
    }
}
