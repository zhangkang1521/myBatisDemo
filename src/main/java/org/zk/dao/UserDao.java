package org.zk.dao;

import org.apache.ibatis.annotations.Param;
import org.zk.model.User;

/**
 * Created by zhangkang on 2017/7/26.
 */
public interface UserDao {

    User findById(@Param("id") Integer id);
}
