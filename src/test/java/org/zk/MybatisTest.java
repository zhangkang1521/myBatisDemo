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

    @Test
    public void testCacheLevel1() throws Exception{
        SqlSession session = sqlSessionFactory.openSession();
        User student = session.selectOne("org.zk.dao.UserDao.findById", 1);
        // 同一session,一级缓存中查询
        User student2 = session.selectOne("org.zk.dao.UserDao.findById", 1);
        System.out.println(student.getUsername());
        session.close();
    }

    @Test
    public void testCacheLevel2() throws Exception{

        SqlSession session = sqlSessionFactory.openSession();
        SqlSession session2 = sqlSessionFactory.openSession();

        User student = session.selectOne("org.zk.dao.UserDao.findById", 1);
        session.close(); // 关闭session才将缓存写入
        User student2 = session2.selectOne("org.zk.dao.UserDao.findById", 1);
        session2.close();
    }

    @Test
    public void testCacheLevel2_clear() throws Exception{

        SqlSession session1 = sqlSessionFactory.openSession();
        SqlSession session2 = sqlSessionFactory.openSession();
        SqlSession session3 = sqlSessionFactory.openSession();

        User student = session1.selectOne("org.zk.dao.UserDao.findById", 1);
        session1.close(); // 关闭session才将缓存写入

        // 清空缓存
        User user = new User();
        user.setId(1);
        user.setUsername("zy");
        session3.update("org.zk.dao.UserDao.update", user);
        session3.commit();
        session3.close();


        User student2 = session2.selectOne("org.zk.dao.UserDao.findById", 1);
        session2.close();
    }




    @Test
    public void testUpdate() {
        SqlSession session = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(1);
        user.setUsername("zy");
        session.update("org.zk.dao.UserDao.update", user);
        session.commit();
        session.close();
    }
}
