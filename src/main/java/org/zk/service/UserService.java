package org.zk.service;

import org.springframework.transaction.annotation.Transactional;
import org.zk.dao.UserDao;
import org.zk.model.User;


public class UserService {

    private UserDao userDao;

    @Transactional
    public void save() {
        User user = new User();
        user.setUsername("ss");
        userDao.insert(user);
        if ("ss".equals(user.getUsername())) {
            throw new RuntimeException("ss");
        }
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
