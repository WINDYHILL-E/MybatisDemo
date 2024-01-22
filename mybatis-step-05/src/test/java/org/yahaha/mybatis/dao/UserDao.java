package org.yahaha.mybatis.dao;

import org.yahaha.mybatis.po.User;

public interface UserDao {

    User queryUserInfoById(Long uId);

}
