package com.sm.common.criterion;

import com.sm.common.persistence.helper.PersistenceHelperFacade;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Newbody on 2016/1/7.
 */
public class SimpleExpression implements Criterion {
    private final String propertyName;
    private final Object value;
    private boolean ignoreCase;
    private final String op;

    protected SimpleExpression(String propertyName, Object value, String op) {
        this.propertyName = propertyName;
        this.value = value;
        this.op = op;
    }

    protected SimpleExpression(String propertyName, Object value, String op, boolean ignoreCase) {
        this.propertyName = propertyName;
        this.value = value;
        this.ignoreCase = ignoreCase;
        this.op = op;
    }

    protected final String getOp() {
        return op;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getValue() {
        return value;
    }
    public SimpleExpression ignoreCase() {
        ignoreCase = true;
        return this;
    }

    @Override
    public String toNamedSqlString() {
        return PersistenceHelperFacade.toColumnName(propertyName) + getOp() + ":" + propertyName;
    }

    @Override
    public Map<String, Object> getNamedParameterValues() {
        Map<String, Object> values = new HashMap<>();
        values.put(propertyName, value);
        return values;
    }

    @Override
    public String toString() {
        return propertyName + getOp() + value;
    }
}
