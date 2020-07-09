package com.jalja.rpc.transport.rpc.tcp;

import com.jalja.rpc.transport.rpc.RpcProperties;


import com.jalja.rpc.transport.rpc.tcp.core.NettyConstant;
import com.jalja.rpc.transport.rpc.tcp.handler.ClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author XL
 * @title: NettyTcpClient
 * @projectName jalja-rpc
 * @date 2020/7/7 17:40
 * @description: TODO
 */
public class NettyTcpClient {
    private Channel channel=null;
    private RpcProperties properties;
    private EventLoopGroup group=getEventLoopGroup(10);
    private Logger logger= LoggerFactory.getLogger(NettyTcpClient.class);
    public NettyTcpClient(RpcProperties properties) {
        this.properties = properties;
        connect();
    }
    public void connect()  {
        Bootstrap bootstrap=new Bootstrap();
        String [] address=properties.getServerAddress().split(":");
        try {
            bootstrap.group(group).channel( getSocketChannel()).handler(new ClientChannelInitializer(properties));
            channel=bootstrap.connect(address[0],Integer.valueOf(address[1])).sync().channel();
        }catch (Exception e){
            e.printStackTrace();
            group.shutdownGracefully();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            channel.close();
        }));
    }
    private void reconnection(){
        new Thread(()->{
            try {
                logger.info("重新连接");
                connect();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }).start();
    }
    /**
     * 发送数据
     * @param byteBuf
     */
    public void write(ByteBuf byteBuf){
        if(channel!=null && channel.isActive()){
            channel.writeAndFlush(byteBuf);
            return;
        }
        synchronized (NettyTcpClient.class){
            if(channel!=null && channel.isActive()){
                channel.writeAndFlush(byteBuf);
                return;
            }
            reconnection();
        }
    }

    /**
     * 获取ServerSocketChannel,根据epoll支持情况返回
     * @return
     */
    private static Class<? extends SocketChannel> getSocketChannel() {
        return Epoll.isAvailable() ? EpollSocketChannel.class : NioSocketChannel.class;
    }

    /**
     * 获取EventLoopGroup对象,根据epoll支持情况返回
     * @param size 线程大小
     * @return
     */
    private static EventLoopGroup getEventLoopGroup(int size) {
        return Epoll.isAvailable() ? new EpollEventLoopGroup(size) : new NioEventLoopGroup(size);
    }

    public static void main(String[] args) throws Exception {
        RpcProperties properties=new RpcProperties();
        properties.setServerAddress("127.0.0.1:9999");
        NettyTcpClient server=new NettyTcpClient(properties);
        server.connect();
        while (true){
            Thread.sleep(2000);
            ByteBuf byteBuf= Unpooled.buffer();  ;
            byteBuf.writeBytes("AAAA".getBytes());
            byteBuf.writeBytes(NettyConstant.DE_LIMITER);
            server.write(byteBuf);
        }
    }
}
