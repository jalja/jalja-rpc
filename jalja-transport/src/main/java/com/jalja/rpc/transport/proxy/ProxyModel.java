package com.jalja.rpc.transport.proxy;

import com.jalja.rpc.transport.rpc.IRpcServer;

/**
 * @author XL
 * @title: ProxyModel
 * @projectName jalja-rpc
 * @date 2020/7/216:45
 * @description: 动态代理
 */
public class ProxyModel {
    /**
     * 服务器IP地址
     */
    private Integer port;
    /**
     * 序列化类型
     */
    private String serializableType;
    /**
     * 提供服务
     */
    private IRpcServer rpcServer;
    /**
     * 动态代理接口
     */
    private Class interfaceClass;

    /**
     * 负载均衡的类型
     */
    private String balancedType;

    /**
     * 注册中心的地址
     */
    private String registryAddress;

    /**
     * 注册中心跟路径
     */
    private String rootPath="rpc";
    /**
     * 注册中心类型
     */
    private String registryType;


    public ProxyModel() {

    }

    public ProxyModel(String balancedType, String serializableType, IRpcServer rpcServer, Class interfaceClass) {
        this.balancedType = balancedType;
        this.serializableType = serializableType;
        this.rpcServer = rpcServer;
        this.interfaceClass = interfaceClass;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSerializableType() {
        return serializableType;
    }

    public void setSerializableType(String serializableType) {
        this.serializableType = serializableType;
    }

    public IRpcServer getRpcServer() {
        return rpcServer;
    }

    public void setRpcServer(IRpcServer rpcServer) {
        this.rpcServer = rpcServer;
    }

    public Class getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getBalancedType() {
        return balancedType;
    }

    public void setBalancedType(String balancedType) {
        this.balancedType = balancedType;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getRegistryType() {
        return registryType;
    }

    public void setRegistryType(String registryType) {
        this.registryType = registryType;
    }
}
