package com.jalja.rpc.transport.rpc.tcp;


import com.jalja.rpc.common.rpc.RemoteInvocation;
import com.jalja.rpc.common.seria.IJaljaSerializable;
import com.jalja.rpc.common.seria.SerializableSPI;
import com.jalja.rpc.transport.rpc.IRpcTransport;
import com.jalja.rpc.transport.rpc.RpcProperties;
import com.jalja.rpc.transport.rpc.tcp.core.HexUtils;
import com.jalja.rpc.transport.rpc.tcp.result.NettyResponse;
import com.jalja.rpc.transport.rpc.tcp.result.NettyResponseResult;
import com.jalja.rpc.transport.rpc.tcp.core.PackageData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;


/**
 * @author XL
 * @title: NettpTcpServer
 * @projectName jalja-rpc
 * @date 2020/7/7 17:35
 * @description: netty tcp 实现
 */
public class NettpTcpTransport implements IRpcTransport {
    private Logger logger= LoggerFactory.getLogger(NettpTcpTransport.class);
    private NettyTcpClient client;
    @Override
    public void start(RpcProperties properties) {
        NettyTcpServer server=new NettyTcpServer(properties);
        server.start();
    }
    @Override
    public Object invokeProtocl(RpcProperties rpcProperties, RemoteInvocation invocation) {
        String requestId= HexUtils.uuid();
        try {
            IJaljaSerializable serializable= SerializableSPI.getIJaljaSerializable(Class.forName(rpcProperties.getSerializableType()));
            createClient(rpcProperties);
            logger.error("Thread-Start:"+Thread.currentThread().getName());
            NettyResponse response=new NettyResponse(new CountDownLatch(1));
            NettyResponseResult.set(requestId,response);
            PackageData packageData=new PackageData();
            packageData.setRequestId(requestId);
            packageData.setBody(serializable.serialize(invocation));
            ByteBuf byteBuf= Unpooled.wrappedBuffer(packageData.encode());
            client.write(byteBuf);
            return NettyResponseResult.getResult(requestId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            NettyResponseResult.remove(requestId);
        }
    }
    private void createClient(RpcProperties rpcProperties){
        if(client!=null){
            return;
        }
        synchronized (NettpTcpTransport.class){
            if(client==null){
                client=new NettyTcpClient(rpcProperties);
            }
        }
    }
}
