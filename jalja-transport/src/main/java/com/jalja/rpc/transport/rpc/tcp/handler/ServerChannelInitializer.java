package com.jalja.rpc.transport.rpc.tcp.handler;

import com.jalja.rpc.transport.rpc.RpcProperties;
import com.jalja.rpc.transport.rpc.tcp.core.NettyConstant;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;


import java.util.concurrent.TimeUnit;

/**
 * @author XL
 * @title: ServerChannelInitializer
 * @projectName jalja-rpc
 * @date 2020/7/7 17:43
 * @description: TODO
 */

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private RpcProperties properties;
    public ServerChannelInitializer(RpcProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        sc.pipeline().addLast(new DelimiterBasedFrameDecoder(2048, true, true, NettyConstant.DE_LIMITER));
        sc.pipeline().addLast(new IdleStateHandler(30,0,0, TimeUnit.SECONDS));
        sc.pipeline().addLast(new SendMessageHandler());
        sc.pipeline().addLast(new SessionHandler());
        sc.pipeline().addLast(new MessageCodecHandler(NettyConstant.NettyType.SERVER));
        sc.pipeline().addLast(new ServerDispatcherHandler(properties));
        sc.pipeline().addLast(new ExceptionHandler());
        sc.pipeline().addLast(new LoggingHandler(LogLevel.INFO));

    }

}
