package com.jalja.rpc.common.seria;

import com.caucho.hessian.io.HessianInput;

import java.io.*;
import java.lang.reflect.Type;

/**
 * @author XL
 * @title: JDKSeria
 * @projectName jalja-rpc
 * @date 2020/6/24 16:23
 * @description: JDK 的序列化方法
 */
public class JDKSerializable implements IJaljaSerializable {
    @Override
    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
        ObjectOutputStream stream=new ObjectOutputStream(arrayOutputStream);
        stream.writeObject(obj);
        byte[]bytes=arrayOutputStream.toByteArray();
        close(arrayOutputStream);
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] data, Type cls) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream=new ByteArrayInputStream(data);
        ObjectInputStream stream=new ObjectInputStream(inputStream);
        close(inputStream);
        return (T) stream.readObject();
    }
    private static void close(Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
