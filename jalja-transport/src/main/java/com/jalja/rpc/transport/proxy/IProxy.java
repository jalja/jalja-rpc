package com.jalja.rpc.transport.proxy;


import com.jalja.rpc.common.rpc.RpcServerAddress;
import com.jalja.rpc.register.RegisterSpi;
import com.jalja.rpc.register.model.QueryRegisterModelDTO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author XL
 * @title: IProxy
 * @projectName jalja-rpc
 * @date 2020/6/24 16:57
 * @description: TODO
 */
public interface IProxy {
      <T> T getProxy(ProxyModel model);

}
