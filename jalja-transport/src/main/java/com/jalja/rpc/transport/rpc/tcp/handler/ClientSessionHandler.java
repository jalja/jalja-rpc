package com.jalja.rpc.transport.rpc.tcp.handler;

import com.alibaba.fastjson.JSON;
import com.jalja.rpc.transport.rpc.tcp.core.NettyConstant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Auther: XL
 * @Date: 2019/12/2 23:47
 * @Description:
 */


public class ClientSessionHandler extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("userEventTriggered:"+JSON.toJSON(evt));
        if(evt instanceof IdleStateEvent && ((IdleStateEvent) evt).state()== IdleState.WRITER_IDLE){
            ctx.channel().eventLoop().execute(()->{
                if(!ctx.channel().isActive()){
                    ctx.close();
                }
            });
        }
        super.userEventTriggered(ctx, evt);
    }

}