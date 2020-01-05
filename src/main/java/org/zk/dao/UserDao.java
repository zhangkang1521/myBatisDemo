package org.zk.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.zk.model.User;

/**
 * Created by Administrator on 11/5/2017.
 */
@Repository
@Qualifier("userDao")
public interface UserDao {

    User findById(int userId);

    void insert(User user);
}
