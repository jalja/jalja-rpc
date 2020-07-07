package com.jalja.rpc.config;

/**
 * @author XL
 * @title: RpcConfigProperties
 * @projectName jalja-rpc
 * @date 2020/7/2 10:49
 * @description: TODO
 */
public class RpcConfigProperties extends AbsProperties {
    private String applicationId;
    private String applicationName;
    /**
     * 应用类 0（服务器和客服端） 1：服务器  2:客户端
     */
    private String applicationType;
    private String registryType;
    private String registryAddress;
    private String serializableType;
    private String serverAddress;
    private String serverPort;
    private String protocolType;
    private String scanNamespace;
    private String route;
    private String proxyType="com.jalja.rpc.transport.proxy.JDKProxy";
    private String rootPath="rpc";
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getRegistryType() {
        return registryType;
    }

    public void setRegistryType(String registryType) {
        this.registryType = registryType;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getSerializableType() {
        return serializableType;
    }

    public void setSerializableType(String serializableType) {
        this.serializableType = serializableType;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getScanNamespace() {
        return scanNamespace;
    }

    public void setScanNamespace(String scanNamespace) {
        this.scanNamespace = scanNamespace;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getProxyType() {
        return proxyType;
    }

    public void setProxyType(String proxyType) {
        this.proxyType = proxyType;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }
}
