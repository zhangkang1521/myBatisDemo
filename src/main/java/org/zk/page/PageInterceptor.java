package org.zk.page;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        }
)
public class PageInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Page page = PageHelp.PAGE_THREAD_LOCAL.get();
        // 不分页
        if (page == null) {
            return invocation.proceed();
        }
        Executor executor = (Executor) invocation.getTarget();
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];

        BoundSql boundSql = ms.getBoundSql(parameter);
        // count
        BoundSql countBoundSql = new BoundSql(ms.getConfiguration(), transferToCountSql(boundSql.getSql()), boundSql.getParameterMappings(), parameter);
        CacheKey countCacheKey = executor.createCacheKey(ms, parameter, rowBounds, countBoundSql);
        List<Long> count = executor.query(getCountMappedStatement(ms), parameter, rowBounds, null, countCacheKey, countBoundSql);
        // 查询指定页数据
        BoundSql limitBoundSql = new BoundSql(ms.getConfiguration(), transferToLimitSql(boundSql.getSql(), page), boundSql.getParameterMappings(), parameter);
        CacheKey limitCacheKey = executor.createCacheKey(ms, parameter, rowBounds, limitBoundSql);
        List content = executor.query(ms, parameter, rowBounds, null, limitCacheKey, limitBoundSql);
        // 设值
        page.setCount(count.get(0));
        page.addAll(content);
        return page;
    }


    private String transferToLimitSql(String sql, Page page) {
        return sql + " limit " + (page.getPageNo() - 1) * page.getPageSize() + "," + page.getPageSize();
    }

    private MappedStatement getCountMappedStatement(MappedStatement mappedStatement) {
        ResultMap resultMap = new ResultMap.Builder(mappedStatement.getConfiguration(), "count", Long.class, Collections.emptyList()).build();
        MappedStatement countMs = new MappedStatement.Builder(mappedStatement.getConfiguration(),
                mappedStatement.getId() + "_count", mappedStatement.getSqlSource(),
                mappedStatement.getSqlCommandType())
                .resultMaps(Arrays.asList(resultMap))
                .build();
        return countMs;
    }

    private String transferToCountSql(String sql) {
        // TODO select xx from => select count(*) from
        return sql.replace("select *", "select count(*)");
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
