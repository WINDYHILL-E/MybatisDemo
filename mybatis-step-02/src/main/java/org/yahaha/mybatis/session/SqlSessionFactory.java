package org.yahaha.mybatis.session;

import org.yahaha.mybatis.binding.MapperRegistry;

public interface SqlSessionFactory {

    SqlSession openSession();

}
