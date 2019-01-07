package org.zk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zk.dao.TeaDao;
import org.zk.dao.UserDao;
import org.zk.model.User;

/**
 * Created by Administrator on 11/5/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SpringMybatisTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    GenericApplicationContext ctx;



    @Test
    public void test1() {
        User user = userDao.findById(1);
        System.out.println(user.getUsername());
    }

    @Test
    public void testScan() {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(ctx, true);
        scanner.scan()
    }
}
