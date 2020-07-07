package com.jalja.rpc.config;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author XL
 * @title: JaljaRpcServerRegistrar
 * @projectName jalja-rpc
 * @date 2020/7/321:18
 * @description: TODO
 */
public class EnableJaljaRpc implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, true);
        scanner.scan("com.jalja.rpc");
    }
}
