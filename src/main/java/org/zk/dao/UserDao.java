package org.zk.dao;

import org.zk.model.User;

/**
 * Created by Administrator on 11/5/2017.
 */
public interface UserDao {

    User findById(int userId);
}
