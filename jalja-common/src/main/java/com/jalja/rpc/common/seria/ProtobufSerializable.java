package com.jalja.rpc.common.seria;

import com.jalja.rpc.common.utils.ProtobufUtils;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.lang.reflect.Type;

/**
 * @author XL
 * @title: ProtobufSeria
 * @projectName jalja-rpc
 * @date 2020/6/24 16:21
 * @description:  Protobuf 的序列化方式
 */
public class ProtobufSerializable implements IJaljaSerializable {
    private static final ThreadLocal<LinkedBuffer> BUFFER_THREAD_LOCAL = ThreadLocal.withInitial(() -> LinkedBuffer.allocate(512));

    private static class SerializeData {
        private Object target;

        public Object getTarget() {
            return target;
        }

        public void setTarget(Object target) {
            this.target = target;
        }
    }

    @Override
    public byte[] serialize(Object obj) {
        SerializeData data = new SerializeData();
        data.setTarget(obj);
        Schema<SerializeData> schema = RuntimeSchema.getSchema((Class<SerializeData>) data.getClass());
        LinkedBuffer buffer = BUFFER_THREAD_LOCAL.get();
        // ser
        try {
            return ProtostuffIOUtil.toByteArray(data, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Type cls) {
        Schema<SerializeData> schema = RuntimeSchema.getSchema(SerializeData.class);
        SerializeData message = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, message, schema);
        return (T) message.getTarget();
    }
}
