package org.zk.comm;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by zhangkang on 2019/1/20.
 */
// 拦截StatementHandler的prepare方法，参数为Connection
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class}) })
public class SQLStatsInterceptor implements Interceptor {


    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        System.out.println(sql);
        return invocation.proceed();
    }

    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    public void setProperties(Properties properties) {
        System.out.println(properties);
    }
}
