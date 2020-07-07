package com.jalja.example.producer;



import com.jalja.rpc.config.EnableJaljaRpc;
import org.apache.log4j.spi.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * @author XL
 * @title: SpringContainer
 * @projectName jalja-rpc
 * @date 2020/7/316:49
 * @description: TODO
 */
@Configurable

@ComponentScan(basePackages={"com.jalja.example.producer"})
@PropertySource(value = {"classpath:spring.properties"})
@Import(EnableJaljaRpc.class)
public class ProducerContainer {

}
