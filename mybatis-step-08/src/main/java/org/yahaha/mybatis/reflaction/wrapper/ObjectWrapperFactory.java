package org.yahaha.mybatis.reflaction.wrapper;

import org.yahaha.mybatis.reflaction.MetaObject;

/**
 * 对象包装器工厂，生产对象包装器
 */
public interface ObjectWrapperFactory {

    /**
     * 判断有没有包装器
     */
    boolean hasWrapperFor(Object object);

    /**
     * 得到包装器
     */
    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);

}
