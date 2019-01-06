package org.zk.dao;

import org.zk.model.User;

/**
 * Created by zhangkang on 2019/1/6.
 */
public interface UserDao {
    User findById(int id);
}
