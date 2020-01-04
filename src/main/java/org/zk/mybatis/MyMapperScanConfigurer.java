package org.zk.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class MyMapperScanConfigurer implements BeanDefinitionRegistryPostProcessor {

    private String basePackage;
    private SqlSessionFactory sqlSessionFactory;

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        MyClassPathScanner myClassPathScanner = new MyClassPathScanner(registry);
        myClassPathScanner.setSqlSessionFactory(sqlSessionFactory);
        myClassPathScanner.scan(basePackage);
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}
