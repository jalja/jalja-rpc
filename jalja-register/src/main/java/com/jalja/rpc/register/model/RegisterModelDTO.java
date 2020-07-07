package com.jalja.rpc.register.model;

import java.io.Serializable;

/**
 * @author XL
 * @title: RegisterModelDTO
 * @projectName jalja-rpc
 * @date 2020/6/3016:29
 * @description: TODO
 */
public class RegisterModelDTO implements Serializable {
    /**
     * 主机地址 IP ，作为Value
     */
    private String ip;
    /**
     * 需要注册的接口名(全限定类名) ，作为Key
     */
    private String className;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
