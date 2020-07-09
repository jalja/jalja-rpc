package com.jalja.rpc.transport.rpc.tcp.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
/**
 * @Auther: XL
 * @Date: 2019/12/9 20:44
 * @Description: 发送数据 添加分隔符
 */

public class SendMessageHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }
}
