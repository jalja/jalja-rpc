package com.jalja.rpc.test;

import com.jalja.rpc.common.RemoteInvocation;
import com.jalja.rpc.common.seria.IJaljaSerializable;
import com.jalja.rpc.common.seria.JDKSeria;
import com.jalja.rpc.common.seria.SeriaSpi;

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
        IJaljaSerializable serializable=SeriaSpi.getIJaljaSerializable(JDKSeria.class);
        byte [] bs=serializable.serialize(invocation);
        RemoteInvocation invocation2=serializable.deserialize(bs,RemoteInvocation.class);
        System.out.println(invocation2.getInterfaceName());
    }
}
