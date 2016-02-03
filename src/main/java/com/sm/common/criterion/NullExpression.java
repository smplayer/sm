package com.sm.common.criterion;

import com.sm.common.persistence.helper.PersistenceHelperFacade;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Newbody on 2016/1/7.
 */
public class NullExpression implements Criterion {
    private final String propertyName;

    protected NullExpression(String propertyName) {
        this.propertyName = propertyName;
    }


    @Override
    public String toNamedSqlString() {
        return PersistenceHelperFacade.toColumnName(propertyName) + "is null";
    }

    @Override
    public Map<String, Object> getNamedParameterValues() {
        return new HashMap<>();
    }

}
