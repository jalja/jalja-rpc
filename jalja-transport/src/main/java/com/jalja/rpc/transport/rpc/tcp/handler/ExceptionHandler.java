package com.jalja.rpc.transport.rpc.tcp.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Auther: XL
 * @Date: 2019/12/6 09:31
 * @Description:
 */


public class ExceptionHandler extends ChannelDuplexHandler {
    Logger logger= LoggerFactory.getLogger(ExceptionHandler.class);
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("exceptionCaught",cause);
    }
}
