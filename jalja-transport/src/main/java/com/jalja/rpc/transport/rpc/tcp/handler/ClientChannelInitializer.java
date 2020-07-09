package com.jalja.rpc.transport.rpc.tcp.handler;



import com.jalja.rpc.transport.rpc.RpcProperties;
import com.jalja.rpc.transport.rpc.tcp.core.NettyConstant;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;


import java.util.concurrent.TimeUnit;

/**
 * @Auther: XL
 * @Date: 2019/12/2 22:32
 * @Description:
 */

public class ClientChannelInitializer extends ChannelInitializer<Channel> {
    private RpcProperties properties;

    public ClientChannelInitializer(RpcProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void initChannel(Channel sc) throws Exception {
        sc.pipeline().addLast(new DelimiterBasedFrameDecoder(2048, true, true, NettyConstant.DE_LIMITER));
        sc.pipeline().addLast(new IdleStateHandler(0,20,0, TimeUnit.SECONDS));
        sc.pipeline().addLast(new SendMessageHandler());
        sc.pipeline().addLast(new MessageCodecHandler(NettyConstant.NettyType.CLIENT));
        sc.pipeline().addLast(new ClientDispatcherHandler(properties));
        sc.pipeline().addLast(new ExceptionHandler());
        sc.pipeline().addLast(new ClientSessionHandler());
        sc.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
    }
}
