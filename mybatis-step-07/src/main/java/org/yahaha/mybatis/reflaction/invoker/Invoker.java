package org.yahaha.mybatis.reflaction.invoker;

/**
 * 调用者
 */
public interface Invoker {

    Object invoke(Object target, Object[] args) throws Exception;

    Class<?> getType();
}
