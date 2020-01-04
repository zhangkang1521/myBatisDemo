package org.zk.mybatis;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.InputStream;

public class MySqlSessionFactoryBean implements FactoryBean {

    private DataSource dataSource;
    private Resource[] mapperLocations;

    public Object getObject() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("mybatis.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        // 设置DataSource，事务管理
        Configuration configuration = sqlSessionFactory.getConfiguration();
        Environment.Builder environmentBuilder = new Environment.Builder("development")
                .transactionFactory(new JdbcTransactionFactory())
                .dataSource(dataSource);
        sqlSessionFactory.getConfiguration().setEnvironment(environmentBuilder.build());
        // mapper
        for (Resource mapperLocation: mapperLocations) {
            String resource = mapperLocation.toString();
            ErrorContext.instance().resource(resource);
            XMLMapperBuilder mapperParser = new XMLMapperBuilder(mapperLocation.getInputStream(), configuration, resource, configuration.getSqlFragments());
            mapperParser.parse();
        }
        return sqlSessionFactory;
    }

    public Class<?> getObjectType() {
        return SqlSessionFactory.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setMapperLocations(Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }
}
