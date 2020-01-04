package org.zk;

import org.junit.Test;
import org.springframework.context.support.GenericApplicationContext;
import org.zk.dao.UserDao;
import org.zk.mybatis.MyClassPathScanner;

public class ClassPathScanTest {

    @Test
    public void test1() {
        GenericApplicationContext context = new GenericApplicationContext();
        MyClassPathScanner scanner = new MyClassPathScanner(context);
        int count = scanner.scan("org.zk.dao");
        context.refresh();
        UserDao userDao = (UserDao) context.getBean("userDao");
    }

}
