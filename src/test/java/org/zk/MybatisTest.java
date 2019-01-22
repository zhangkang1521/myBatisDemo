package org.zk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.zk.dao.UserDao;
import org.zk.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

/**
 * Created by Administrator on 7/16/2017.
 */
public class MybatisTest {

    SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }


    @Test
    public void test1() throws Exception{
        SqlSession session = sqlSessionFactory.openSession();
        User student = session.selectOne("org.zk.dao.UserDao.findById", 1);
        System.out.println(student.getUsername());
        session.close();
    }

    @Test
    public void test2() throws Exception{
        SqlSession session = sqlSessionFactory.openSession();
        UserDao userDao = session.getMapper(UserDao.class);
        System.out.println(userDao.findById(1).getUsername());
        session.close();
    }

    @Test
    public void testPage() {
        SqlSession session = sqlSessionFactory.openSession();
        UserDao userDao = session.getMapper(UserDao.class);
        User user = new User();
//        PageHelper.startPage(2, 10);
        Object o = userDao.findPageable(user);

        session.close();
    }

}
