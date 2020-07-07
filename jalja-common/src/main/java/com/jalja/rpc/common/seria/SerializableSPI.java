package com.jalja.rpc.common.seria;

import java.util.ServiceLoader;

/**
 * @author XL
 * @title: SeriaSpi
 * @projectName jalja-rpc
 * @date 2020/6/24 16:34
 * @description: 获取对应的序列化
 */
public class SerializableSPI {
    public static IJaljaSerializable getIJaljaSerializable(Class cz){
        ServiceLoader<IJaljaSerializable> shouts = ServiceLoader.load(IJaljaSerializable.class);
        for (IJaljaSerializable s : shouts) {
             if(s.getClass().getName().equals(cz.getName())){
                 return  s;
             }
        }
        return null;
    }
}
