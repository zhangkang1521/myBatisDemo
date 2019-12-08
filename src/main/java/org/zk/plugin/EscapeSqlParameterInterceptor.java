package org.zk.plugin;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.PreparedStatement;
import java.util.Map;
import java.util.Properties;

@Intercepts({
        @Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class})
})
public class EscapeSqlParameterInterceptor implements Interceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        MetaObject metaObject = SystemMetaObject.forObject(invocation.getTarget());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("mappedStatement");
        if (mappedStatement.getSqlCommandType() == SqlCommandType.SELECT) {
            BoundSql boundSql = (BoundSql) metaObject.getValue("boundSql");
            if (StringUtils.containsIgnoreCase(boundSql.getSql(), "like")) {
                Object parameterObject = metaObject.getValue("parameterObject");
                metaObject.setValue("parameterObject", escapeParamObject(parameterObject));
            }
        }
        return invocation.proceed();
    }

    /**
     * 对ParamObject中的百分号进行转义
     * @param parameterObject
     * @return
     */
    public Object escapeParamObject(Object parameterObject) {
        // TODO 这样写有点复杂，看有没有其他方法
        if (parameterObject instanceof String) {
            return ((String) parameterObject).replace("%", "\\%");
        } else if (parameterObject instanceof Map) {
            ((Map) parameterObject).forEach((k, v) -> {
                // param1 param2 是mybatis自动指定的参数名
                if (!StringUtils.startsWith((String)k, "param")) {
                    if (v instanceof String || v instanceof Object) {
                        ((Map) parameterObject).put(k, escapeParamObject(v));
                    }
                }
            });
        } else if (parameterObject instanceof Object) {
            MetaObject metaObject = SystemMetaObject.forObject(parameterObject);
            String[] setterNames = metaObject.getSetterNames();
            for (String setterName : setterNames) {
                Class setterType = metaObject.getSetterType(setterName);
                if (String.class.equals(setterType)) {
                    String str = (String)metaObject.getValue(setterName);
                    metaObject.setValue(setterName, escapeParamObject(str));
                }
            }
        }
        return parameterObject;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }
}
