package com.sm.common.criterion;

import com.sm.common.persistence.helper.PersistenceHelperFacade;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Newbody on 2016/1/7.
 */
public class PropertyExpression implements Criterion {
    private final String propertyName;
    private final String otherPropertyName;
    private final String op;

    protected PropertyExpression(String propertyName, String otherPropertyName, String op) {
        this.propertyName = propertyName;
        this.otherPropertyName = otherPropertyName;
        this.op = op;
    }

    public String getOp() {
        return op;
    }

    @Override
    public String toNamedSqlString() {
        return PersistenceHelperFacade.toColumnName(propertyName) + getOp() + PersistenceHelperFacade.toColumnName(otherPropertyName);
    }

    @Override
    public Map<String, Object> getNamedParameterValues() {
        return new HashMap<>();
    }
}
