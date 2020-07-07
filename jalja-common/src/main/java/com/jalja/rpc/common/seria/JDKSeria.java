package com.jalja.rpc.common.seria;

import java.io.*;
import java.lang.reflect.Type;

/**
 * @author XL
 * @title: JDKSeria
 * @projectName jalja-rpc
 * @date 2020/6/2416:23
 * @description: TODO
 */
public class JDKSeria implements IJaljaSerializable {
    @Override
    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
        ObjectOutputStream stream=new ObjectOutputStream(arrayOutputStream);
        stream.writeObject(obj);
        return arrayOutputStream.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] data, Type cls) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream=new ByteArrayInputStream(data);
        ObjectInputStream stream=new ObjectInputStream(inputStream);
        return (T) stream.readObject();
    }
}
