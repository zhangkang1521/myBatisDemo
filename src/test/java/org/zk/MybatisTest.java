package org.zk;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.zk.dao.UserDao;
import org.zk.model.User;

import java.io.InputStream;
import java.io.Reader;

/**
 * Created by Administrator on 7/16/2017.
 */
public class MybatisTest {

    SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws Exception{
//        InputStream in = this.getClass().getClassLoader().getResourceAsStream("mybatis.xml");
        Reader reader = Resources.getResourceAsReader("mybatis.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }


    // statementId操作方式，不要用
    @Test
    public void test1() throws Exception{
        SqlSession session = sqlSessionFactory.openSession();
        User student = session.selectOne("org.zk.dao.UserDao.findById", 1);
        System.out.println(student.getUsername());
        session.close();
    }

    // 推荐使用接口方式
    @Test
    public void test2() throws Exception{
        SqlSession session
                = sqlSessionFactory.openSession();
        UserDao userDao = session.getMapper(UserDao.class);
        System.out.println(userDao.findById(1));
        session.close();
    }
}
