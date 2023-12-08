package org.yahaha.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务接口，定义事务的基本操作
 */
public interface Transaction {

    /**
     * 获取数据库连接
     * @return 数据库连接
     * @throws SQLException SQLException
     */
    Connection getConnection() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;

}
