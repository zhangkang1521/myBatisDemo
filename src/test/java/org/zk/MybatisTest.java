package org.zk;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zk.dao.UserDao;
import org.zk.model.User;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Created by Administrator on 7/16/2017.
 */
public class MybatisTest {

    SqlSessionFactory sqlSessionFactory;
    SqlSession session;

    @Before
    public void before() throws Exception{
        Reader reader = Resources.getResourceAsReader("mybatis.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        // tk
        MapperHelper mapperHelper = new MapperHelper();
        mapperHelper.processConfiguration(session.getConfiguration());
    }

    @After
    public void after() {
        session.close();
    }

    @Test
    public void testDao(){
        UserDao userDao = session.getMapper(UserDao.class);
        System.out.println(userDao.selectByPrimaryKey(1));
    }

    @Test
    public void test1(){
        User student = session.selectOne("org.zk.dao.UserDao.findById", 1);
        System.out.println(student.getUsername());
    }

    @Test
    public void testInsert(){
        UserDao userDao = session.getMapper(UserDao.class);
        User user = new User();
        user.setUsername("zk");
        int r = userDao.insert(user);
        session.commit();
        System.out.println(user.getId());
    }



}
