package com.jalja.rpc.transport.rpc;

import com.jalja.rpc.common.rpc.RemoteInvocation;



/**
 * @author XL
 * @title: IRpcServer
 * @projectName jalja-rpc
 * @date 2020/6/24 20:38
 * @description: TODO
 */
public interface IRpcTransport {
    void start(RpcProperties properties) ;
    Object invokeProtocl(RpcProperties rpcProperties, RemoteInvocation invocation);
}
