package com.sm.common.persistence.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Newbody on 10/10/2015.
 */
public class BasicMySQLDao {
    private final static Logger logger = LoggerFactory.getLogger(BasicMySQLDao.class);
    @Resource(name = "namedParameterJdbcTemplate")
    protected NamedParameterJdbcTemplate jdbcTemplate;

    public Boolean exists(String entityName, String propertyName, String value) {
        Map<String, Object> params = new HashMap<>();
        params.put("value", value);

        String sql = "SELECT EXISTS (SELECT * FROM " + toTableName(entityName) + " WHERE " + toColumnName(propertyName) + "=:value)";
        Integer result = jdbcTemplate.queryForObject(sql, params, Integer.class);
        if (result == 0) {
            return false;
        } else {
            return true;
        }
    }

    public Map<String, Object> retrieveForMap(String entityName, Collection<String> requiredPropertyNames, Map<String, Object> constraint) {
        StringBuilder sql = buildQuerySQL(entityName, requiredPropertyNames, constraint.keySet());
        return jdbcTemplate.queryForMap(sql.toString(), constraint);
    }

    public List<Map<String, Object>> retrieveForMapList(String entityName, Collection<String> requiredPropertyNames, Map<String, Object> constraint) {
        StringBuilder sql = buildQuerySQL(entityName, requiredPropertyNames, constraint.keySet());
        logger.debug(sql.toString());
        return jdbcTemplate.queryForList(sql.toString(), constraint);
    }

    public List<Map<String, Object>> retrieveForMapList(String entityName, Collection<String> requiredPropertyNames, Map<String, Object> constraint, Long start, Integer size) {
        StringBuilder sql = buildQuerySQL(entityName, requiredPropertyNames, constraint.keySet());
        sql.append(" LIMIT ").append(start).append(", ").append(size);
        return jdbcTemplate.queryForList(sql.toString(), constraint);
    }

    /* query for count */
    public Long count(String entityName, Map<String, Object> params) {
        StringBuilder sql = buildCountSQL(entityName, params.keySet());
        return jdbcTemplate.queryForObject(sql.toString(), params, Long.class);
    }

    public Long count(String entityName) {
        return count(entityName, new HashMap<String, Object>());
    }

    public Object min(String entityName, String columnName, Map<String, Object> constrained){
        StringBuilder sql = buildFunctionSQL("MIN", entityName, columnName, constrained.keySet());
        return jdbcTemplate.queryForObject(sql.toString(), constrained, Number.class);
    }

    public Object max(String entityName, String columnName, Map<String, Object> constrained){
        StringBuilder sql = buildFunctionSQL("MAX", entityName, columnName, constrained.keySet());
        return jdbcTemplate.queryForObject(sql.toString(), constrained, Number.class);
    }

    protected StringBuilder buildQuerySQL(String entityName, Collection<String> requiredPropertyNames) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        Iterator<String> propertyNameIterator = requiredPropertyNames.iterator();
        while (propertyNameIterator.hasNext()) {
            String propertyName = propertyNameIterator.next();
            sql.append(toColumnName(propertyName)).append(" as ").append(propertyName);
            if (propertyNameIterator.hasNext()) {
                sql.append(", ");
            }
        }
        sql.append(" FROM ").append(toTableName(entityName));
        return sql;
    }

    protected StringBuilder buildQuerySQL(String entityName, Collection<String> requiredPropertyNames, Collection<String> constrainedPropertyNames) {
        StringBuilder sql = buildQuerySQL(entityName, requiredPropertyNames);

        if (constrainedPropertyNames != null && !constrainedPropertyNames.isEmpty()) {
            sql.append(" WHERE ");
            Iterator<String> constrainedIterator = constrainedPropertyNames.iterator();
            while (constrainedIterator.hasNext()) {
                String constaint = constrainedIterator.next();
                sql.append(toColumnName(constaint)).append("=:").append(constaint);
                if (constrainedIterator.hasNext()) {
                    sql.append(" AND ");
                }
            }
        }
        return sql;
    }

    protected StringBuilder buildFunctionSQL(String function, String entityName, String columnName, Collection<String> constrainedPropertyNames) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(function)
                .append("(")
                .append(toColumnName(columnName))
                .append(") FROM ")
                .append(toTableName(entityName))
                .append(" WHERE ");
        Iterator<String> iterator = constrainedPropertyNames.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            sql.append(toColumnName(name)).append("=:").append(name);
            if (iterator.hasNext()) {
                sql.append(" AND ");
            }
        }
        return sql;
    }

    protected StringBuilder buildCountSQL(String entityName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM ").append(toTableName(entityName));
        return sql;
    }

    protected StringBuilder buildCountSQL(String entityName, Collection<String> constrainedPropertyNames) {
        StringBuilder sql = buildCountSQL(entityName);
        if (!constrainedPropertyNames.isEmpty()) {
            sql.append(" WHERE ");
            Iterator<String> iterator = constrainedPropertyNames.iterator();
            while (iterator.hasNext()) {
                String name = iterator.next();
                sql.append(toColumnName(name)).append("=:").append(name);
                if (iterator.hasNext()) {
                    sql.append(" AND ");
                }
            }
        }
        return sql;
    }

    /* creation SQL builder */
    protected StringBuilder buildCreationSQL(String entityName, Collection<String> targetPropertyNames) {
        Iterator<String> iterator = targetPropertyNames.iterator();
        StringBuilder namesSql = new StringBuilder();
        StringBuilder valuesSql = new StringBuilder();
        while (iterator.hasNext()) {
            String name = iterator.next();

            namesSql.append(toColumnName(name));
            if (iterator.hasNext()) {
                namesSql.append(",");
            }

            valuesSql.append(":").append(name);
            if (iterator.hasNext()) {
                valuesSql.append(",");
            }
        }
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ")
                .append(toTableName(entityName))
                .append("(")
                .append(namesSql)
                .append(")VALUES(")
                .append(valuesSql)
                .append(")");
        return sql;
    }

    /* updating sql builder */
    protected StringBuilder buildUpdatingSql(String entityName, Collection<String> targetPropertyNames, Collection<String> constrainedPropertyNames) {
        StringBuilder targetsSql = new StringBuilder();
        Iterator<String> targetIterator = targetPropertyNames.iterator();
        while (targetIterator.hasNext()) {
            String target = targetIterator.next();
            targetsSql.append(toColumnName(target)).append("=:").append(target);
            if (targetIterator.hasNext()) {
                targetsSql.append(",");
            }
        }
        StringBuilder constrainedSql = new StringBuilder();
        Iterator<String> constainedIterator = constrainedPropertyNames.iterator();
        while (constainedIterator.hasNext()) {
            String constraint = constainedIterator.next();
            constrainedSql.append(toColumnName(constraint)).append("=:").append(constraint);
            if (constainedIterator.hasNext()) {
                constrainedSql.append(",");
            }
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ")
                .append(toTableName(entityName))
                .append(" SET ")
                .append(targetsSql)
                .append(" WHERE ")
                .append(constrainedSql);
        return sql;
    }

    /* deleting sql builder */
    protected StringBuilder buildDeletingSql(String entityName, Collection<String> constrainedPropertyNames) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ")
                .append(toTableName(entityName))
                .append(" WHERE ");
        Iterator<String> iterator = constrainedPropertyNames.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            sql.append(toColumnName(name)).append("=:").append(name);
            if (iterator.hasNext()) {
                sql.append(",");
            }
        }
        return sql;
    }

    protected Boolean updateStatus(String entityName, String id, String status){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("status", status);

        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(toTableName(entityName))
                .append(" SET c_status=:status WHERE c_id=:id");

        jdbcTemplate.update(sql.toString(), paramMap);
        return true;
    }

    /* json mapper */
    protected String toJsonString(Object o) {
        String jsonString = JSON.toJSONString(o);
        return jsonString;
    }

    protected Object fromJsonString(String jsonString, Class classType) {
        Object o = JSON.parseObject(jsonString, classType);
        return o;
    }

    protected Object fromJsonString(String jsonString, TypeReference typeReference) {
        Object o = JSON.parseObject(jsonString, typeReference);
        return o;
    }

    protected String toTableName(String entityName) {
        return "tb_" + entityName;
    }

    protected String toColumnName(String propertyName) {
        return "c_" + propertyName;
    }

}
