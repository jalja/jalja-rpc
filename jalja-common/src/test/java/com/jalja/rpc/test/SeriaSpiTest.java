package com.jalja.rpc.test;

import com.jalja.rpc.common.rpc.RemoteInvocation;
import com.jalja.rpc.common.seria.IJaljaSerializable;
import com.jalja.rpc.common.seria.JDKSerializable;
import com.jalja.rpc.common.seria.SerializableSPI;

import java.io.IOException;

/**
 * @author XL
 * @title: SeriaSpiTest
 * @projectName jalja-rpc
 * @date 2020/6/2416:43
 * @description: TODO
 */
public class SeriaSpiTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        RemoteInvocation invocation=new RemoteInvocation();
        invocation.setInterfaceName("A");
        invocation.setMethodName("getClass");
        IJaljaSerializable serializable= SerializableSPI.getIJaljaSerializable(JDKSerializable.class);
        byte [] bs=serializable.serialize(invocation);
        RemoteInvocation invocation2=serializable.deserialize(bs,RemoteInvocation.class);
        System.out.println(invocation2.getInterfaceName());
    }
}
