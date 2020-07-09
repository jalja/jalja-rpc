package com.jalja.rpc.transport.rpc.tcp.core;

/**
 * @Auther: XL
 * @Date: 2019/12/10 11:15
 * @Description:
 */
public interface Codec<T,E> {
     abstract T encode();
     abstract <R> R decode(T t);
}
