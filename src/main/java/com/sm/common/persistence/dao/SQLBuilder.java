package com.sm.common.persistence.dao;

import com.sm.common.Order;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Newbody on 12/2/2015.
 */
public class SQLBuilder {

    /* creation SQL builder */
    public static StringBuilder buildCreationSQL(String repositoryName, Collection<String> targetPropertyNames) {
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
                .append(repositoryName)
                .append("(")
                .append(namesSql)
                .append(")VALUES(")
                .append(valuesSql)
                .append(")");
        return sql;
    }

    private static StringBuilder buildConstrains(Map<String, Object> paramMap) {
        StringBuilder sql = new StringBuilder();
        Iterator<Map.Entry<String, Object>> iterator = paramMap.entrySet().iterator();
        if (iterator.hasNext()) {
            sql.append(" WHERE ");
        }
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String name = entry.getKey();
            Object value = entry.getValue();
            sql.append(toColumnName(name));
            if (value != null) {
                sql.append("=:");
            } else {
                sql.append(" is :");
            }
            sql.append(name);
            if (iterator.hasNext()) {
                sql.append(",");
            }
        }
        return sql;
    }

    /* deleting sql builder */
    public static StringBuilder buildDeletingSql(String repositoryName, Map<String, Object> paramMap) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ")
                .append(repositoryName)
                .append(buildConstrains(paramMap));
        return sql;
    }

    /* updating sql builder */
    public static StringBuilder buildUpdatingSql(String repositoryName, Collection<String> targetPropertyNames, Map<String, Object> paramMap) {
        StringBuilder targetsSql = new StringBuilder();
        Iterator<String> targetIterator = targetPropertyNames.iterator();
        while (targetIterator.hasNext()) {
            String target = targetIterator.next();
            targetsSql.append(toColumnName(target)).append("=:").append(target);
            if (targetIterator.hasNext()) {
                targetsSql.append(",");
            }
        }

        StringBuilder constrainedSql = buildConstrains(paramMap);

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ")
                .append(repositoryName)
                .append(" SET ")
                .append(targetsSql);
        if (constrainedSql.length() > 0) {
//            sql.append(" WHERE ")
            sql.append(constrainedSql);
        }
        return sql;
    }

    /*query sql*/
    public static StringBuilder buildQuerySQL(String repositoryName, Collection<String> requiredPropertyNames) {
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
        sql.append(" FROM ").append(repositoryName);
        return sql;
    }

    public static StringBuilder buildQuerySQL(String repositoryName, Collection<String> requiredPropertyNames, Map<String, Object> paramMap) {
        StringBuilder sql = buildQuerySQL(repositoryName, requiredPropertyNames);
        if (paramMap != null && !paramMap.isEmpty()) {
            sql.append(buildConstrains(paramMap));
        }
        return sql;
    }

    public static StringBuilder buildQuerySQL(String repositoryName, Collection<String> requiredPropertyNames, Map<String, Object> paramMap, Order orderConstraint) {
        StringBuilder sql = buildQuerySQL(repositoryName, requiredPropertyNames, paramMap);
        sql.append(" ORDER BY ").append(toColumnName(orderConstraint.getPropertyName())).append(" ").append(orderConstraint.getOrderMode().getValue());
        return sql;
    }

    public static StringBuilder buildQuerySQL(String repositoryName, Collection<String> requiredPropertyNames, Map<String, Object> paramMap, Long start, Integer size) {
        StringBuilder sql = buildQuerySQL(repositoryName, requiredPropertyNames, paramMap);
        sql.append(" LIMIT ").append(start).append(", ").append(size);
        return sql;
    }

    public static StringBuilder buildQuerySQL(String repositoryName, Collection<String> requiredPropertyNames, Map<String, Object> paramMap, Order orderConstraint, Long start, Integer size) {
        StringBuilder sql = buildQuerySQL(repositoryName, requiredPropertyNames, paramMap, orderConstraint);
        sql.append(" LIMIT ").append(start).append(", ").append(size);
        return sql;
    }

    public static StringBuilder buildQuerySQL(String repositoryName, Collection<String> requiredPropertyNames, Map<String, Object> paramMap, Collection<Order> orderConstraints, Long start, Integer size) {
        StringBuilder sql = buildQuerySQL(repositoryName, requiredPropertyNames, paramMap);
        StringBuilder orderSql = new StringBuilder();
        if (!orderConstraints.isEmpty()) {
            Iterator<Order> iterator = orderConstraints.iterator();
            while (iterator.hasNext()) {
                Order orderConstraint = iterator.next();
                orderSql.append(toColumnName(orderConstraint.getPropertyName())).append(" ").append(orderConstraint.getOrderMode().getValue());
                if (iterator.hasNext()) {
                    orderSql.append(", ");
                }
            }
            sql.append(" ORDER BY ").append(orderSql);
        }
        sql.append(" LIMIT ").append(start).append(", ").append(size);
        return sql;
    }

    public static StringBuilder buildQuerySQLForPicked(String repositoryName, Collection<String> requiredPropertyNames, Map<String, Object> paramMap) {
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
        sql.append(" FROM ")
                .append(repositoryName);
        Iterator<String> iterator = paramMap.keySet().iterator();
        if (iterator.hasNext()) {
            sql.append(" WHERE ");
        }
        while (iterator.hasNext()) {
            String name = iterator.next();
            sql.append(toColumnName(name)).append(" IN(:").append(name).append(")");
            if (iterator.hasNext()) {
                sql.append(" AND ");
            }
        }
        return sql;
    }

    public static StringBuilder buildQuerySQLForPicked(String repositoryName, Collection<String> requiredPropertyNames, Map<String, Object> paramMap, Order orderConstraint) {
        return buildQuerySQLForPicked(repositoryName, requiredPropertyNames, paramMap)
                .append(" ORDER BY ")
                .append(toColumnName(orderConstraint.getPropertyName()))
                .append(" ")
                .append(orderConstraint.getOrderMode().getValue());
    }

    public static StringBuilder buildFunctionSQL(String function, String repositoryName, String columnName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(function)
                .append("(")
                .append(toColumnName(columnName))
                .append(") FROM ")
                .append(repositoryName);
        return sql;
    }

    public static StringBuilder buildFunctionSQL(String function, String repositoryName, String columnName, Map<String, Object> paramMap) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(function)
                .append("(")
                .append(toColumnName(columnName))
                .append(") FROM ")
                .append(repositoryName);

        if (paramMap != null && !paramMap.isEmpty()) {
            sql.append(buildConstrains(paramMap));
        }
        return sql;
    }

    public static StringBuilder buildCountSQL(String repositoryName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM ").append(repositoryName);
        return sql;
    }

    public static StringBuilder buildCountSQL(String repositoryName, Map<String, Object> paramMap) {
        StringBuilder sql = buildCountSQL(repositoryName);
        if (paramMap != null && !paramMap.isEmpty()) {
            sql.append(buildConstrains(paramMap));
        }
        return sql;
    }

    public static String toColumnName(String propertyName) {
        return "c_" + propertyName;
    }
}
