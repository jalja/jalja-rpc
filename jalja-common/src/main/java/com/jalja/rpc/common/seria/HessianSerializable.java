package com.jalja.rpc.common.seria;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author XL
 * @title: HessianSerializable
 * @projectName jalja-rpc
 * @date 2020/7/7 14:15
 * @description: Hessian 序列化
 */
public class HessianSerializable implements IJaljaSerializable {
    @Override
    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = null;
        HessianOutput hessianOutput = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            // Hessian的序列化输出
            hessianOutput = new HessianOutput(byteArrayOutputStream);
            hessianOutput.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw  e;
        } finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                throw  e;
            }
            try {
                if (hessianOutput != null) {
                    hessianOutput.close();
                }
            } catch (IOException e) {
                throw  e;
            }
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Type cls) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = null;
        HessianInput hessianInput = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(data);
            // Hessian的反序列化读取对象
            hessianInput = new HessianInput(byteArrayInputStream);
            return (T) hessianInput.readObject();
        } catch (IOException e) {
            throw  e;
        } finally {
            close(byteArrayInputStream, hessianInput);
        }
    }
    private static void close(ByteArrayInputStream byteArrayInputStream, HessianInput hessianInput) {
        try {
            if (byteArrayInputStream != null) {
                byteArrayInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (hessianInput != null) {
            hessianInput.close();
        }
    }
}
