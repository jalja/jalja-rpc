package com.jalja.rpc.transport.rpc;

/**
 * @author XL
 * @title: RpcProperties
 * @projectName jalja-rpc
 * @date 2020/7/214:01
 * @description: TODO
 */
public class RpcProperties {

    private String serializableType;
    private String serverAddress;
    private String protocolType;




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


}
