package org.yahaha.mybatis.builder.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import org.yahaha.mybatis.builder.BaseBuilder;
import org.yahaha.mybatis.io.Resources;
import org.yahaha.mybatis.mapping.MappedStatement;
import org.yahaha.mybatis.mapping.SqlCommandType;
import org.yahaha.mybatis.session.Configuration;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * xml配置构建器，用了构建者模式。继承BaseBuilder
 */
public class XMLConfigBuilder extends BaseBuilder {

    private Element root;

    public XMLConfigBuilder(Reader reader) {

        // 1.调用父类初始化Configuration
        super(new Configuration());

        // 2.dom4j处理xml
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(new InputSource(reader));
            root = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析配置
     *
     * @return 配置类实例
     */
    public Configuration parse() {
        try {
            mapperElement(root.element("mappers"));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
        return configuration;
    }

    private void mapperElement(Element mappers) throws IOException, DocumentException, ClassNotFoundException {
        List<Element> elementList = mappers.elements("mapper");
        for (Element e : elementList) {
            String resource = e.attributeValue("resource");
            Reader reader = Resources.getResourceAsReader(resource);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new InputSource(reader));
            Element root = document.getRootElement();

            // 拿到mapper的namespace
            String namespace = root.attributeValue("namespace");

            List<Element> selectNodes = root.elements("select");
            for (Element node : selectNodes) {
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                // 匹配占位符?
                Map<Integer, String> parameter = new HashMap<>();
                Pattern pattern = Pattern.compile("(#\\{(.*?)})");
                Matcher matcher = pattern.matcher(sql);
                for (int i = 1; matcher.find(); i++) {
                    String g1 = matcher.group(1);
                    String g2 = matcher.group(2);
                    parameter.put(i, g2);
                    sql = sql.replace(g1, "?");
                }

                String msId = namespace + "." + id;
                String nodeName = node.getName();
                SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
                MappedStatement mappedStatement = new MappedStatement.Build(configuration, msId, sqlCommandType, parameterType, resultType, sql, parameter).build();

                configuration.addMappedStatement(mappedStatement);
            }
            // 从之前的包扫描，变成读取xml后根据namespace添加
            configuration.addMapper(Resources.classForName(namespace));
        }

    }

}
