package com.jalja.rpc.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author XL
 * @title: ZKCuratorUtils
 * @projectName jalja-rpc
 * @date 2020/7/116:47
 * @description: TODO
 */
public class ZKCuratorUtils {
    //会话超时时间
    private int SESSION_TIMEOUT = 30 * 1000;
    //连接超时时间
    private int CONNECTION_TIMEOUT = 3 * 1000;

    private String zkServer="127.0.0.1:2181";

    private String rootPath=null;

    private CuratorFramework client=null;

    public ZKCuratorUtils(int SESSION_TIMEOUT, int CONNECTION_TIMEOUT, String zkServer, String rootPath) {
        this.SESSION_TIMEOUT = SESSION_TIMEOUT;
        this.CONNECTION_TIMEOUT = CONNECTION_TIMEOUT;
        this.zkServer = zkServer;
        this.rootPath = rootPath;
    }

    public ZKCuratorUtils(String zkServer,String rootPath) {
        this.zkServer = zkServer;
        this.rootPath=rootPath;
    }

    public void init(){
        if(StringUtils.isEmpty(rootPath)){
            throw new RuntimeException("rootPath is null");
        }
        //1 重试策略：初试时间为1s 重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //2 通过工厂创建连接
        this.client = CuratorFrameworkFactory.builder()
                .connectString(this.zkServer).connectionTimeoutMs(this.CONNECTION_TIMEOUT)
                .sessionTimeoutMs(this.SESSION_TIMEOUT)
                .retryPolicy(retryPolicy).namespace(this.rootPath)
                .build();
        this.client.start();
    }

    /**
     * 创建节点(如果节点已经存在就更新数据)
     * @param key
     * @param value
     * @param mode
     * @throws Exception
     */
    public void createUpdateNode(String key, String value, CreateMode mode) throws Exception {
        if(checkNode(key)){
            key="/" +key;
            this.client.setData().forPath(key,value.getBytes());
        }else {
            key="/" +key;
            this.client.create().withMode(mode).forPath(key,value.getBytes());
        }
    }
    /**
     * 更新节点数据
     * @param key
     * @param value
     * @throws Exception
     */
    public void updateNode(String key,String value) throws Exception {
        if(checkNode(key)){
            this.client.setData().forPath(key, value.getBytes("UTF-8"));
        }else {
            throw new RuntimeException("节点不存在："+key);
        }
    }
    /**
     * 检查节点是否存在
     * @param key
     * @return
     * @throws Exception
     */
    public boolean checkNode(String key) throws Exception {
        key="/" +key;
        try {
            Stat stat =this.client.checkExists().forPath(key);
            return stat != null ? true : false;
        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }
    }
    /**
     * 删除节点 包括子节点
     * @param key
     * @throws Exception
     */
    public void deleteNode(String key) throws Exception {
        key="/" +key;
        this.client.delete().deletingChildrenIfNeeded().forPath(key);
    }
    /**
     * 关闭连接
     */
    public void closeClient(){
        this.client.close();
    }

    /**
     * 获取节点数据
     * @param key
     * @return
     * @throws Exception
     */
    public String getNode(String key) throws Exception {
        if(!checkNode(key)){
            return null;
        }
        key="/" +key;
        byte[] value=this.client.getData().forPath(key);
        if(value!=null && value.length>0){
            return new String(value,"UTF-8");
        }
        throw  new RuntimeException("未发现节点，或者节点数据为空："+key);
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * 监听节点变化
     * @param key
     * @param childrenCacheListener
     * @throws Exception
     */
    public void nodeListener(String key, PathChildrenCacheListener childrenCacheListener) throws Exception {
        key="/" +key;
        final PathChildrenCache childrenCache = new PathChildrenCache(this.client, key, true);
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        childrenCache.getListenable().addListener(childrenCacheListener);
    }
}
