package org.zk.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;

public class MyMapperFactoryBean implements FactoryBean {

    private Class<?> mapperInterface;
    private SqlSessionFactory sqlSessionFactory;

    public Object getObject() throws Exception {
        return sqlSessionFactory.openSession().getMapper(mapperInterface);
    }

    public Class<?> getObjectType() {
        return mapperInterface;
    }

    public boolean isSingleton() {
        return true;
    }



    public void setMapperInterface(Class<?> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }


    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}
