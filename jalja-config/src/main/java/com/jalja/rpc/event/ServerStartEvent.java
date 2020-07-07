package com.jalja.rpc.event;



import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;


/**
 * @author XL
 * @title: PropertiesEvent
 * @projectName jalja-rpc
 * @date 2020/7/3 22:08
 * @description: 配置文件加载完毕事件
 */
public class ServerStartEvent extends ApplicationContextEvent {
    private ApplicationContext context;

    public ServerStartEvent( ApplicationContext context) {
        super(context);
    }
}
