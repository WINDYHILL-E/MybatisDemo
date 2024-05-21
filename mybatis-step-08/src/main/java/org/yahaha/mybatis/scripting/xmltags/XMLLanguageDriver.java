package org.yahaha.mybatis.scripting.xmltags;

import org.dom4j.Element;
import org.yahaha.mybatis.mapping.SqlSource;
import org.yahaha.mybatis.scripting.LanguageDriver;
import org.yahaha.mybatis.session.Configuration;

/**
 * @author 小傅哥，微信：fustack
 * @description XML语言驱动器
 * @date 2022/5/17
 * @github https://github.com/fuzhengwei/CodeDesignTutorials
 * @Copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class XMLLanguageDriver implements LanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType) {
        // 用XML脚本构建器解析
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
        return builder.parseScriptNode();
    }

}