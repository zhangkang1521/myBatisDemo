package org.zk.type;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 逗号分隔的字符串(db) <==> 字符串数组(String[])
 * Created by zhangkang on 2017/7/31.
 */
public class StringArrayTypeHandler extends BaseTypeHandler<String[]>{


    public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
        String str = StringUtils.join(parameter, ",");
        ps.setString(i, str);
    }

    public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        if(value != null) {
            return value.split(",");
        } else {
            return null;
        }
    }

    public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        if(value != null) {
            return value.split(",");
        } else {
            return null;
        }
    }

    public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        if(value != null) {
            return value.split(",");
        } else {
            return null;
        }
    }
}
