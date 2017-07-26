package org.zk;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.zk.model.User;

import java.io.InputStream;

/**
 * Created by Administrator on 7/16/2017.
 */
public class MybatisTest {

    SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("mybatis.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
    }


    @Test
    public void test1() throws Exception{
        SqlSession session = sqlSessionFactory.openSession();
        User student = session.selectOne("org.zk.dao.UserDao.findById", 1);
        System.out.println(student.getUsername());
        session.close();
    }
}
