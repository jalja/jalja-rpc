package com.jalja.rpc.common.rpc;

import java.io.Serializable;

/**
 * @author XL
 * @title: RemoteMethod
 * @projectName jalja-rpc
 * @date 2020/6/24 15:40
 * @description: 通信的实体
 */
public class RemoteInvocation implements Serializable {
    /**
     * 接口名
     */
    private String interfaceName;
    /**
     * 要调用的方法名
     */
    private String methodName;
    /**
     * 参数类型列表
     */
    private Class[] paramtypes;
    /**
     * 参数
     */
    private Object[] objects;
    /**
     * 出参数类型
     */
    private Class resultClass;

    public RemoteInvocation(String interfaceName, String methodName, Class[] paramtypes, Object[] objects) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.paramtypes = paramtypes;
        this.objects = objects;
    }

    public RemoteInvocation() {

    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParamtypes() {
        return paramtypes;
    }

    public void setParamtypes(Class[] paramtypes) {
        this.paramtypes = paramtypes;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    public Class getResultClass() {
        return resultClass;
    }

    public void setResultClass(Class resultClass) {
        this.resultClass = resultClass;
    }
}
