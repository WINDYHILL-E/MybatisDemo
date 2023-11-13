package org.yahaha.mybatis.dao.impl;

import org.yahaha.mybatis.dao.UserDao;

public class UserDaoImpl implements UserDao {
    @Override
    public String queryUsername(Integer userId) {
        System.out.println("执行了重写的方法。");
        return null;
    }
}
