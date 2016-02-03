package com.sm.common.criterion;

import com.sm.common.persistence.helper.PersistenceHelperFacade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Newbody on 2016/1/7.
 */
public class InExpression implements Criterion {
    private final String propertyName;
    private final List<Object> values;

    protected InExpression(String propertyName, List<Object> values) {
        this.propertyName = propertyName;
        this.values = values;
    }

    @Override
    public String toNamedSqlString() {
        StringBuilder builder = new StringBuilder();
        builder.append(PersistenceHelperFacade.toColumnName(propertyName))
                .append(" IN (:")
                .append(propertyName)
                .append(")");
        return builder.toString();
    }

    @Override
    public Map<String, Object> getNamedParameterValues() {
        Map<String, Object> values = new HashMap<>();
        values.put(propertyName, this.values);
        return values;
    }
}
