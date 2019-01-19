package org.zk.dao;

/**
 * Created by zhangkang on 2019/1/19.
 */
public class SqlProvider {
    public String selectUser(long userId) {
        return "select * from tb_user where id=" + userId;
    }
}
