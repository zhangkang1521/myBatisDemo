package org.zk.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zk.model.User;
import org.zk.param.UserParam;

import java.util.List;

/**
 * Created by zhangkang on 2019/1/6.
 */
public interface UserDao {

    @Select("select * from tb_user where username like '%'")
    List<User> findAll();

    @Select("select * from tb_user where username like concat('%', #{user.username}, '%')")
    List<User> findByParam(@Param("user") UserParam user);

    User findById(int id);

    @Select("select * from tb_user where username like concat('%', #{username}, '%')")
    List<User> findByUserName(String username);

    @Select("select * from tb_user where id = #{id} and username like concat('%', #{username}, '%')")
    List<User> findByIdAndUserName(@Param("id") Integer id, @Param("username") String username);

    int insert(User user);
}
