package com.jalja.rpc.transport.rpc.tcp.core;

import com.jalja.rpc.common.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Auther: XL
 * @Date: 2019/12/19 17:49
 * @Description:
 */
public class PackageData implements Codec<byte[], ByteBuf> {
    /**
     * 请求者标识 （15字节）
     */
    private String requestId;
    /**
     * 消息体
     */
    private byte[] body;
    /**
     * 分割符号
     */
    private byte[] deLimiter= NettyConstant.DE_LIMITERS;

    public PackageData() {

    }

    public PackageData(byte type,String requestId, byte[] body) {
        this.requestId = requestId;
        this.body = body;
    }

    @Override
    public byte[] encode() {
        ByteBuf byteBuf= Unpooled.buffer();
        byteBuf.writeBytes(requestId.getBytes(Charsets.US_ASCII));
        byteBuf.writeBytes(body);
        byteBuf.writeBytes(deLimiter);
        byte [] bytes=new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return bytes;
    }

    @Override
    public PackageData decode(byte[] bytes) {
        ByteBuf byteBuf= Unpooled.wrappedBuffer(bytes);
        byte [] rs=new byte[15];
        byteBuf.readBytes(rs);
        body=new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(body);
        this.setRequestId(new String(rs,Charsets.US_ASCII));
        this.setBody(body);
        return this;
    }

    public PackageData( byte[] body) {

        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
