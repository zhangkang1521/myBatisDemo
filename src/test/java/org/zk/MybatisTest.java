package org.zk;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.zk.dao.ClassDao;
import org.zk.dao.UserDao;
import org.zk.model.Classes;
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
    public void test2() throws Exception {
        SqlSession session = sqlSessionFactory.openSession(true);
        UserDao userDao = session.getMapper(UserDao.class);
        System.out.println(userDao.findById(1));
        session.close();
    }

    @Test
    public void testCacheLevel1_1() throws Exception{
        SqlSession session = sqlSessionFactory.openSession(true);
        User student = session.selectOne("org.zk.dao.UserDao.findById", 1);
        // 同一session,一级缓存中查询
        User student2 = session.selectOne("org.zk.dao.UserDao.findById", 1);
        System.out.println(student.getUsername());
        session.close();
    }

    @Test
    public void testCacheLevel1_2() throws Exception {
        SqlSession session1 = sqlSessionFactory.openSession(true);
        SqlSession session2 = sqlSessionFactory.openSession(true);
        UserDao userDao1 = session1.getMapper(UserDao.class);
        UserDao userDao2 = session2.getMapper(UserDao.class);
        System.out.println(userDao1.findById(1));
        System.out.println(userDao1.findById(1));
        User user = new User();
        user.setId(1);
        user.setUsername("zy4");
        userDao2.update(user);
        System.out.println(userDao1.findById(1)); // session级别缓存，读到了脏数据
    }


    @Test
    public void testCacheLevel2() throws Exception{

        SqlSession session = sqlSessionFactory.openSession();
        SqlSession session2 = sqlSessionFactory.openSession();

        User student = session.selectOne("org.zk.dao.UserDao.findById", 1);
        session.commit(); // commit后才写入缓存
        User student2 = session2.selectOne("org.zk.dao.UserDao.findById", 1);
        session.close();
        session2.close();
    }

    @Test
    public void testCacheLevel2_clear() throws Exception{

        SqlSession session2 = sqlSessionFactory.openSession();
        SqlSession session3 = sqlSessionFactory.openSession();


        // 清空缓存
        User user = new User();
        user.setId(1);
        user.setUsername("zy");
        session3.update("org.zk.dao.UserDao.update", user); // update会清空缓存


        User student2 = session2.selectOne("org.zk.dao.UserDao.findById", 1);
        session2.close();
        session3.close();
    }

    @Test
    public void testCacheLevel2_multiTable() {
        SqlSession session1 = sqlSessionFactory.openSession();
        SqlSession session2 = sqlSessionFactory.openSession();
        SqlSession session3 = sqlSessionFactory.openSession();

        UserDao userDao = session1.getMapper(UserDao.class);
        User user = userDao.findWithClassInfo(1);
        System.out.println(user.getClassName());

        ClassDao classDao = session2.getMapper(ClassDao.class);
        Classes classes = new Classes();
        classes.setId(10);
        classes.setClassName("computer3");
        classDao.update(classes);
        session1.close();

        UserDao userDao2 = session3.getMapper(UserDao.class);
        User user2 = userDao2.findWithClassInfo(1);
        System.out.println(user2.getClassName());
    }

    @Test
    public void testUpdate() {
        SqlSession session1 = sqlSessionFactory.openSession(true);
        UserDao userDao = session1.getMapper(UserDao.class);
        User user = new User();
        user.setId(1);
        user.setUsername("zy2");
        userDao.update(user);
        session1.close();
    }

    @Test
    public void testUpdateClass() {
        SqlSession session1 = sqlSessionFactory.openSession(true);
        ClassDao userDao = session1.getMapper(ClassDao.class);
        Classes classes = new Classes();
        classes.setId(10);
        classes.setClassName("computer");
        userDao.update(classes);
    }

    @Test
    public void testFindClasses() throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        ClassDao classDao = session.getMapper(ClassDao.class);
        System.out.println(classDao.findById(11).getClassName());
        session.close();
    }


}

