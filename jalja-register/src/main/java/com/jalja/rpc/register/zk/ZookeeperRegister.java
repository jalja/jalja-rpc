package com.jalja.rpc.register.zk;

import com.alibaba.fastjson.JSON;

import com.google.common.collect.Lists;
import com.jalja.rpc.common.rpc.RpcServerAddress;
import com.jalja.rpc.common.utils.ZKCuratorUtils;
import com.jalja.rpc.register.IRegister;
import com.jalja.rpc.register.model.QueryRegisterModelDTO;
import com.jalja.rpc.register.model.RegisterModelDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author XL
 * @title: ZookeeperRegister
 * @projectName jalja-rpc
 * @date 2020/6/3018:12
 * @description: TODO
 */
public class ZookeeperRegister implements IRegister {
    private Logger logger= LoggerFactory.getLogger(ZookeeperRegister.class);
    private ZKCuratorUtils curatorClient;
    private boolean listener=false;
    private AtomicInteger count=new AtomicInteger(0);
    public ZookeeperRegister setListener(boolean listener) {
        this.listener = listener;
        count.incrementAndGet();
        return this;
    }
    public ZookeeperRegister setCuratorClient(ZKCuratorUtils curatorClient) {
        this.curatorClient = curatorClient;
        return this;
    }
    @Override
    public  void register(RegisterModelDTO modelDTO) throws Exception {
        curatorClient.init();
        List<String> list=getRegisterValue(modelDTO.getClassName());
        if(CollectionUtils.isEmpty(list)){
            list= Lists.newArrayList(modelDTO.getIp());
            curatorClient.createUpdateNode(modelDTO.getClassName(),JSON.toJSONString(list), CreateMode.PERSISTENT);
        }else if (!list.contains(modelDTO.getIp())){
            list.add(modelDTO.getIp());
            curatorClient.createUpdateNode(modelDTO.getClassName(),JSON.toJSONString(list), CreateMode.PERSISTENT);
        }
    }
    @Override
    public void flush() throws Exception {
        if(!listener){
            curatorClient.closeClient();
            logger.error(new Date() +"关闭zk客户端");
            return;
        }
        curatorClient.nodeListener("", new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework framework, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()){
                    case CHILD_REMOVED:
                    case CHILD_UPDATED:
                        RpcServerAddress.clear();
                        System.out.println("=>"+ event.getType());
                        break;
                    default:
                        logger.info("无需处理："+event.getType().name());
                        break;
                }
            }
        });
    }

    /**
     * 根据 key查询 对应的ip地址
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public List<String> getRegisterValue(String key) throws Exception {
        curatorClient.init();
        List<String> list= JSON.parseArray(curatorClient.getNode(key),String.class) ;
        return list;
    }

}
