package org.yahaha.mybatis.transaction;

import org.yahaha.mybatis.session.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 事务工厂
 */
public interface TransactionFactory {

    /**
     * 根据数据库连接创建事务
     *
     * @param connection 数据库连接
     * @return 事务
     */
    Transaction newTransaction(Connection connection);

    /**
     * 根据数据源和隔离级别创建事务
     *
     * @param dataSource 数据源
     * @param level      隔离级别
     * @param autoCommit 是否自动提交事务
     * @return 事务
     */
    Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);
}
