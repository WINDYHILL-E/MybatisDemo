package org.yahaha.mybatis.session;

public interface SqlSession {

    /**
     * 查询一个对象，且根据参数指定的语句执行查询
     *
     * @param statement 查询语句
     * @return 查询结果
     */
    <T> T selectOne(String statement);

    /**
     * 查询一个对象，且根据参数指定的语句执行查询，此外，还能指定语句需要的参数
     *
     * @param statement  查询语句
     * @param parameters 语句用到的参数
     * @return 查询结果
     */
    <T> T selectOne(String statement, Object parameters);

    <T> T getMapper(Class<T> type);
}
