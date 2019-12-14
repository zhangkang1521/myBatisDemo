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
    }

    @After
    public void after() {
        session.close();
    }







    @Test
    public void testParam() {
        UserDao userDao = session.getMapper(UserDao.class);
        userDao.findByIdAndUsername(1, "zk");
    }





}
