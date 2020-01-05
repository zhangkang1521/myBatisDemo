package org.zk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.zk.dao.UserDao;
import org.zk.model.User;
import org.zk.service.UserService;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SpringMybatisTest {

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Test
    public void testTransaction() {
        // 编程式事务
        transactionTemplate.execute(new TransactionCallback<Object>() {
            public Object doInTransaction(TransactionStatus status) {
                User user = new User();
                user.setUsername("ss");
                userDao.insert(user);
                User user2 = new User();
                user2.setUsername("ss");
                userDao.insert(user2);
                if ("ss".equals(user.getUsername())) {
                    throw new RuntimeException("ss");
                }
                return null;
            }
        });
    }

    @Test
    public void notTransaction() {
        // SpringManagedTransaction
        User user = new User();
        user.setUsername("ss");
        userDao.insert(user);
        //throw new RuntimeException("ss");
    }

    @Test
    public void testTransactionAnnotation() {
        userService.save();
    }
}
