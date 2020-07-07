package com.jalja.rpc.transport.rpc;

import com.jalja.rpc.common.RemoteInvocation;



/**
 * @author XL
 * @title: IRpcServer
 * @projectName jalja-rpc
 * @date 2020/6/24 20:38
 * @description: TODO
 */
public interface IRpcServer {
    void start(RpcProperties properties) ;
    Object invokeProtocl(RpcProperties rpcProperties, RemoteInvocation invocation);
}
