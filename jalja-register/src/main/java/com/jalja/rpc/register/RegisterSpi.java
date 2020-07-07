package com.jalja.rpc.register;

import com.jalja.rpc.common.rpc.RpcServerAddress;
import com.jalja.rpc.common.utils.ZKCuratorUtils;
import com.jalja.rpc.register.model.QueryRegisterModelDTO;
import com.jalja.rpc.register.zk.ZookeeperRegister;

import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author XL
 * @title: RegisterSpi
 * @projectName jalja-rpc
 * @date 2020/7/116:01
 * @description: TODO
 */
public class RegisterSpi {
    private static AtomicInteger atomicInteger=new AtomicInteger(0);
    public static IRegister getRegister(Class cz){
        ServiceLoader<IRegister> shouts = ServiceLoader.load(IRegister.class);
        for (IRegister s : shouts) {
            if(s.getClass().getName().equals(cz.getName())){
                return s;
            }
        }
        return null;
    }

    /**
     * 消费者初始化配置中心数据
     */
    public static void consumerInit(QueryRegisterModelDTO modelDTO) throws Exception {
        atomicInteger.incrementAndGet();
        try {
            List<String> list=null;
            IRegister register= RegisterSpi.getRegister(Class.forName(modelDTO.getRegistryType()));
            if(register instanceof ZookeeperRegister){
                ZookeeperRegister zookeeperRegister=(ZookeeperRegister) register;
                ZKCuratorUtils curatorClient=new ZKCuratorUtils(modelDTO.getRegistryAddress(),modelDTO.getRootPath());
                zookeeperRegister.setCuratorClient(curatorClient);
                if(atomicInteger.get()==1){
                    zookeeperRegister.setListener(true);
                }
                list=register.getRegisterValue(modelDTO.getClassName());
                zookeeperRegister.flush();
            }else {
                list=register.getRegisterValue(modelDTO.getClassName());
            }
            if(list==null || list.size() <=0){
                throw new RuntimeException("注册中心为空");
            }
            RpcServerAddress.setIps(modelDTO.getClassName(),list);
        }catch (Exception e){
            throw e;
        }
    }
}
