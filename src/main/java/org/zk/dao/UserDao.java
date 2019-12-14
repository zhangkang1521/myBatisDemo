package org.zk.dao;

import org.apache.ibatis.annotations.Select;
import org.zk.model.User;

/**
 * Created by zhangkang on 2019/1/6.
 */
public interface UserDao {

    User findByIdAndUsername(int id, String username);
}
