package org.zk.interceptors;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.zk.model.User;

import java.util.List;

public class MyObjectFactory extends DefaultObjectFactory {

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        T t = super.create(type, constructorArgTypes, constructorArgs);
        if (t instanceof User) {
            if (((User) t).getUsername() == null) {
                ((User) t).setUsername("default");
            }
        }
        return t;
    }
}
