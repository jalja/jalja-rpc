package com.jalja.rpc.transport.rpc.tcp;

import com.jalja.rpc.transport.rpc.RpcProperties;
import com.jalja.rpc.transport.rpc.tcp.handler.ServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;



/**
 * @author XL
 * @title: NettyTcpServer
 * @projectName jalja-rpc
 * @date 2020/7/717:40
 * @description: TODO
 */
public class NettyTcpServer {
    private final EventLoopGroup bossGroup = getEventLoopGroup(1);
    private final EventLoopGroup workerGroup = getEventLoopGroup(1);
    private RpcProperties properties;
    public NettyTcpServer(RpcProperties properties) {
        this.properties = properties;
    }
    public void start(){
        String port=properties.getServerAddress().split(":")[1];
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(getServerSocketChannel())
                    .childHandler(new ServerChannelInitializer(properties));
            ChannelFuture future = serverBootstrap.bind(Integer.valueOf(port)).sync();
            if(future.isSuccess()){
                System.out.println("服务器启动成功");
            }
            future.channel().closeFuture().sync();
        } catch (Throwable ex) {
            destroy();
        }
    }

    private void destroy() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
    /**
     * 获取ServerSocketChannel,根据epoll支持情况返回
     *
     * @return
     */
    private Class<? extends ServerSocketChannel> getServerSocketChannel() {
        return Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
    }

    /**
     * 获取EventLoopGroup对象,根据epoll支持情况返回
     *
     * @param size 线程大小
     * @return
     */
    private EventLoopGroup getEventLoopGroup(int size) {
        return Epoll.isAvailable() ? new EpollEventLoopGroup(size) : new NioEventLoopGroup(size);
    }

    public static void main(String[] args) {
        RpcProperties properties=new RpcProperties();
        properties.setServerAddress("127.0.0.1:9999");
        NettyTcpServer server=new NettyTcpServer(properties);
        server.start();
    }
}
