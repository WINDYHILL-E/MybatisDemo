package org.yahaha.mybatis.session;

import org.yahaha.mybatis.builder.xml.XMLConfigBuilder;
import org.yahaha.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(Reader reader) {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
        return build(xmlConfigBuilder.parse());
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }

}
