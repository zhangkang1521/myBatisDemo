package org.zk.dao;

import org.apache.ibatis.annotations.Param;
import org.zk.model.Classes;
import org.zk.model.User;

/**
 * Created by zhangkang on 2017/7/26.
 */
public interface ClassDao {

    int update(Classes classes);

    Classes findById(@Param("id") Integer id);
}
