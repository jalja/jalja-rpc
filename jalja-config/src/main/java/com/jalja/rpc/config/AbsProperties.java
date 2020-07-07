package com.jalja.rpc.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XL
 * @title: AbsProperties
 * @projectName jalja-rpc
 * @date 2020/7/322:22
 * @description: TODO
 */
public class AbsProperties {
    public static final Map<String, String> LEGACY_PROPERTIES = new HashMap<String, String>();
    static {
        LEGACY_PROPERTIES.put("applicationId", "jalja.rpc.application.id");
        LEGACY_PROPERTIES.put("applicationName", "jalja.rpc.application.name");
        LEGACY_PROPERTIES.put("applicationType", "jalja.rpc.application.type");
        LEGACY_PROPERTIES.put("serverPort", "jalja.rpc.server.port");
        LEGACY_PROPERTIES.put("serverAddress", "jalja.rpc.server.address");
        LEGACY_PROPERTIES.put("route", "jalja.rpc.consumer.route");

        LEGACY_PROPERTIES.put("proxyType", "jalja.rpc.proxy.type");
        LEGACY_PROPERTIES.put("registryType", "jalja.rpc.registry.type");
        LEGACY_PROPERTIES.put("registryAddress", "jalja.rpc.registry.address");
        LEGACY_PROPERTIES.put("serializableType", "jalja.rpc.serializable.type");
        LEGACY_PROPERTIES.put("protocolType", "jalja.rpc.protocol.Type");

    }
}
