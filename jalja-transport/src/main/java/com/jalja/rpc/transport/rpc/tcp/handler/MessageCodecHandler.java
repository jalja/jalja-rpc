package com.jalja.rpc.transport.rpc.tcp.handler;


import com.jalja.rpc.transport.rpc.tcp.core.NettyConstant;
import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import javafx.util.Pair;



import java.util.List;

/**
 * @Auther: XL
 * @Date: 2019/12/2 22:50
 * @Description:
 */

public class MessageCodecHandler extends ByteToMessageCodec<ByteBuf> {

    private NettyConstant.NettyType nettyType;

    public MessageCodecHandler(NettyConstant.NettyType nettyType) {
        this.nettyType = nettyType;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        byteBuf2.writeBytes(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] bytes=new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        Pair<String,byte[]> pair=new Pair<>(ctx.channel().attr(NettyConstant.SESSION).get(),bytes);
        list.add(pair);
    }
}
