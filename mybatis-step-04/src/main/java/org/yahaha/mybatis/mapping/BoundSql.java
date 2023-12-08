package org.yahaha.mybatis.mapping;

import java.util.Map;

public class BoundSql {

    private String sql;
    private Map<Integer, String> parameter;
    private String parameterType;
    private String resultType;

    public BoundSql(String sql, Map<Integer, String> parameter, String parameterType, String resultType) {
        this.sql = sql;
        this.parameter = parameter;
        this.parameterType = parameterType;
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public Map<Integer, String> getParameter() {
        return parameter;
    }

    public String getParameterType() {
        return parameterType;
    }

    public String getResultType() {
        return resultType;
    }
}
