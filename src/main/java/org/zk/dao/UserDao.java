package org.zk.dao;

import org.apache.ibatis.annotations.Param;
import org.zk.model.User;

import java.util.List;

/**
 * Created by zhangkang on 2019/1/6.
 */
public interface UserDao {
    User findById(int id);

    List<User> findPageable(@Param("username") String username);
}
