package com.jalja.rpc.transport.rpc.tcp.handler;


import com.alibaba.fastjson.JSON;
import com.jalja.rpc.common.rpc.RemoteInvocation;
import com.jalja.rpc.common.rpc.RpcInterfaceInstance;
import com.jalja.rpc.common.seria.IJaljaSerializable;
import com.jalja.rpc.common.seria.SerializableSPI;
import com.jalja.rpc.transport.rpc.RpcProperties;
import com.jalja.rpc.transport.rpc.tcp.core.HexUtils;
import com.jalja.rpc.transport.rpc.tcp.core.NettyConstant;
import com.jalja.rpc.transport.rpc.tcp.core.PackageData;
import com.jalja.rpc.transport.rpc.tcp.result.NettyResponse;
import com.jalja.rpc.transport.rpc.tcp.result.NettyResponseResult;
import io.netty.buffer.ByteBuf;
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

public class ClientDispatcherHandler extends SimpleChannelInboundHandler<Pair<String,byte[]>> {
    private Logger logger= LoggerFactory.getLogger(ClientDispatcherHandler.class);
    private RpcProperties properties;
    public ClientDispatcherHandler(RpcProperties properties) {
        this.properties = properties;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Pair<String, byte[]> pair) throws Exception {
        IJaljaSerializable serializable=SerializableSPI.getIJaljaSerializable(Class.forName(properties.getSerializableType()));
        byte[] bytes=pair.getValue();
        logger.info("bytes:{}", HexUtils.bytesToHex(bytes));
        PackageData packageData=new PackageData();
        packageData.decode(bytes);
        logger.error("RequestId:"+packageData.getRequestId());
        Object result=serializable.deserialize(packageData.getBody(),Object.class);
        NettyResponse response= NettyResponseResult.getResponse(packageData.getRequestId());
        response.setResult(result);
        response.getLatch().countDown();
        NettyResponseResult.set(packageData.getRequestId(),response);
    }
}
