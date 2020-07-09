package com.jalja.rpc.transport.rpc.tcp.handler;

import com.alibaba.fastjson.JSON;

import com.jalja.rpc.transport.rpc.tcp.core.NettyConstant;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
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
public class SessionHandler extends ChannelDuplexHandler {
    private Logger logger= LoggerFactory.getLogger(SessionHandler.class);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String cId=ctx.channel().id().toString();
        logger.info("channelActive:"+cId);
        ctx.channel().attr(NettyConstant.SESSION).set(cId);
        super.channelActive(ctx);
    }
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelWritabilityChanged");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.error("userEventTriggered:",JSON.toJSON(evt));
        if (evt instanceof IdleStateEvent && ((IdleStateEvent) evt).state() == IdleState.READER_IDLE) {
            ctx.close().addListener((ChannelFutureListener) future -> {
                future.channel().close();
                logger.error("channel:",future.channel().isActive());
            });
        }

    }
}