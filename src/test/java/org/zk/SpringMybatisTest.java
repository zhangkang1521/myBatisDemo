package org.zk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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

    @Test
    public void test1() {
        User user = userDao.findById(1);
    }
}
