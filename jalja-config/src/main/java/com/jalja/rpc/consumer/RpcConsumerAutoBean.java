package com.jalja.rpc.consumer;
import com.jalja.rpc.config.ConsumerProperties;
import com.jalja.rpc.transport.proxy.ProxyModel;
import com.jalja.rpc.transport.proxy.ProxySPI;
import com.jalja.rpc.transport.rpc.RpcServerSpi;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;


/**
 * @author XL
 * @title: ConsumerPropertiesListener
 * @projectName jalja-rpc
 * @date 2020/7/322:57
 * @description: 属性注入动态代理类
 */
@Component
public class RpcConsumerAutoBean implements BeanFactoryAware {
    private static final Logger logger = LoggerFactory.getLogger(RpcConsumerAutoBean.class);
    private ConsumerProperties properties;
    private BeanFactory beanFactory;
    public void setBean() throws BeansException {
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory)beanFactory;
        String[] beans=factory.getBeanDefinitionNames();
        try {
            for(String bName:beans){
                Class<?> beanType =factory.getBean(bName).getClass();
                Field[] fields=beanType.getDeclaredFields();
                for (Field field:fields){
                    JReference reference=field.getAnnotation(JReference.class);
                    if(reference!=null){
                        field.setAccessible(true);
                        field.set(factory.getBean(bName), getCreateBean(field.getType()));
                    }
                }
            }
        }catch (Exception e){
            logger.error("给属性注入代理类:",e);
        }
    }

    /**
     * 获取带来对象
     * @param cName
     * @param <T>
     * @return
     */
    public  <T> T getCreateBean(Class<T> cName) throws Exception {
        try {
            ProxyModel model=new ProxyModel();
            model.setPort(Integer.valueOf(properties.getServerPort()));
            model.setBalancedType(properties.getRoute());
            model.setSerializableType(properties.getSerializableType());
            model.setRpcServer(RpcServerSpi.getPRC(Class.forName(properties.getProtocolType())));
            model.setInterfaceClass(cName);
            model.setRegistryAddress(properties.getRegistryAddress());
            model.setRegistryType(properties.getRegistryType());
            model.setRootPath(properties.getRootPath());
            return ProxySPI.getIProxy(Class.forName(properties.getProxyType())).getProxy(model);
        }catch (Exception e){
            logger.error("获取代理类异常:",e);
            throw e;
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        properties= (ConsumerProperties) beanFactory.getBean(ConsumerProperties.class.getName());
        this.beanFactory=beanFactory;
        if(StringUtils.equals(properties.getApplicationType(),"1")){
            return;
        }
        setBean();
    }
}
