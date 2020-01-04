package org.zk;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zk.dao.UserDao;
import org.zk.model.User;

public class SpringMybatisTest {

    @Test
    public void test1() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao) ctx.getBean("userDao");
        User user = userDao.findById(1);
        System.out.println(user.getUsername());
        System.out.println(userDao);
    }
}
