package com.jalja.rpc.register.model;

/**
 * @author XL
 * @title: QueryRegisterModelDTO
 * @projectName jalja-rpc
 * @date 2020/7/6 15:42
 * @description: 注册中心DTO
 */
public class QueryRegisterModelDTO {
    /**
     * 需要注册的接口名(全限定类名) ，作为Key
     */
    private String className;
    /**
     * 注册中心地址
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

    public QueryRegisterModelDTO() {
        this.className = className;
        this.registryAddress = registryAddress;
        this.rootPath = rootPath;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
