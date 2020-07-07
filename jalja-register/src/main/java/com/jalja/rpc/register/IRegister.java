package com.jalja.rpc.register;

import com.jalja.rpc.register.model.QueryRegisterModelDTO;
import com.jalja.rpc.register.model.RegisterModelDTO;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

/**
 * @author XL
 * @title: IRegister
 * @projectName jalja-rpc
 * @date 2020/6/30 16:24
 * @description: 注册中心接口
 */
public interface IRegister {

    /**
     * 注册接口
     * @param modelDTO
     * @throws Exception
     */
    void register(RegisterModelDTO modelDTO) throws Exception;

    void  flush() throws Exception;

    /**
     * 获取服务
     * @param key
     * @return
     */
    List<String> getRegisterValue(String key) throws Exception;
}
