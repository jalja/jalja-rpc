package com.jalja.rpc.transport.rpc.tcp.handler;


import com.alibaba.fastjson.JSON;
import com.jalja.rpc.common.rpc.RemoteInvocation;
import com.jalja.rpc.common.rpc.RpcInterfaceInstance;
import com.jalja.rpc.common.seria.IJaljaSerializable;
import com.jalja.rpc.common.seria.SerializableSPI;
import com.jalja.rpc.transport.rpc.RpcProperties;
import com.jalja.rpc.transport.rpc.tcp.core.HexUtils;
import com.jalja.rpc.transport.rpc.tcp.core.NettyConstant;
import com.jalja.rpc.transport.rpc.tcp.result.NettyResponse;
import com.jalja.rpc.transport.rpc.tcp.result.NettyResponseResult;
import com.jalja.rpc.transport.rpc.tcp.core.PackageData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


/**
 * @Auther: XL
 * @Date: 2019/12/3 23:27
 * @Description:
 */
public class ServerDispatcherHandler extends SimpleChannelInboundHandler<Pair<String,byte[]>> {
    private Logger logger= LoggerFactory.getLogger(ServerDispatcherHandler.class);
    private RpcProperties properties;
    private IJaljaSerializable serializable;
    public ServerDispatcherHandler(RpcProperties properties) {
        this.properties = properties;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Pair<String, byte[]> pair) throws Exception {
        if(serializable==null){
            serializable=SerializableSPI.getIJaljaSerializable(Class.forName(properties.getSerializableType()));
        }
        byte[] bytes=pair.getValue();
        logger.info("bytes:{}", HexUtils.bytesToHex(bytes));
        PackageData packageData=new PackageData();
        packageData.decode(bytes);
        logger.error("server->RequestId:"+packageData.getRequestId());
        RemoteInvocation invocation=serializable.deserialize(packageData.getBody(), RemoteInvocation.class);
        String interfaceName = invocation.getInterfaceName();
        Class interfaceImplClass = RpcInterfaceInstance.getClass(interfaceName);
        Method method = interfaceImplClass.getMethod(invocation.getMethodName(),invocation.getParamtypes());
        Object result = method.invoke(interfaceImplClass.newInstance(),invocation.getObjects());
        logger.error("result:"+ JSON.toJSON(result));
        byte[] resultBytes= serializable.serialize(result);
        packageData.setBody(resultBytes);
        ByteBufAllocator alloc = ctx.channel().alloc();
        ByteBuf byteBuf= alloc.directBuffer();
        logger.info("byteBuf:"+byteBuf.hasArray());
        byteBuf.writeBytes(packageData.encode());
        ctx.channel().writeAndFlush(byteBuf);
    }
}
