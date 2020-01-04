package org.zk.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Set;

public class MyClassPathScanner extends ClassPathBeanDefinitionScanner {

    private SqlSessionFactory sqlSessionFactory;

    public MyClassPathScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
        this.addIncludeFilter(new TypeFilter() {
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return true;
            }
        });
    }

    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface();
    }

    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            BeanDefinition beanDefinition = beanDefinitionHolder.getBeanDefinition();
            beanDefinition.getPropertyValues().add("mapperInterface", beanDefinition.getBeanClassName());
            beanDefinition.getPropertyValues().add("sqlSessionFactory", sqlSessionFactory);
            beanDefinition.setBeanClassName(org.zk.mybatis.MyMapperFactoryBean.class.getName());
        }
        return beanDefinitionHolders;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}
