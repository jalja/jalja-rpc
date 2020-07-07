package com.jalja.rpc.transport.route;

import com.jalja.rpc.common.rpc.RpcServerAddress;
import com.jalja.rpc.register.RegisterSpi;
import com.jalja.rpc.register.model.QueryRegisterModelDTO;
import com.jalja.rpc.transport.proxy.IProxy;
import com.jalja.rpc.transport.proxy.ProxyModel;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.ServiceLoader;

/**
 * @author XL
 * @title: IRoute
 * @projectName jalja-rpc
 * @date 2020/7/410:07
 * @description: TODO
 */
public interface IRoute {

    String getIP(List<String> ips);

    static IRoute getIRoute(String type) {
        try {
            ServiceLoader<IRoute> shouts = ServiceLoader.load(IRoute.class);
            Class cz=Class.forName(type);
            for (IRoute s : shouts) {
                if(s.getClass().getName().equals(cz.getName())){
                    return s;
                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取服务器
     * @return
     * @throws Exception
     */
    static String getUrl(ProxyModel model) throws Exception {
        List<String> ips= RpcServerAddress.getIps(model.getInterfaceClass().getName());
        if(CollectionUtils.isNotEmpty(ips)){
            String ip=IRoute.getIRoute(model.getBalancedType()).getIP(ips);
            String url= ip +":"+model.getPort();
            return url;
        }
        initIps(model);
       return getUrl(model);
    }
    static void initIps(ProxyModel model) {
        synchronized (IRoute.class){
            List<String> ips= RpcServerAddress.getIps(model.getInterfaceClass().getName());
            if(CollectionUtils.isNotEmpty(ips)){
                return;
            }
            System.out.println("初始化接口IP："+model.getInterfaceClass().getName());
            try{
                QueryRegisterModelDTO queryDTO=new QueryRegisterModelDTO();
                queryDTO.setClassName(model.getInterfaceClass().getName());
                queryDTO.setRegistryAddress(model.getRegistryAddress());
                queryDTO.setRootPath(model.getRootPath());
                queryDTO.setRegistryType(model.getRegistryType());
                RegisterSpi.consumerInit(queryDTO);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
