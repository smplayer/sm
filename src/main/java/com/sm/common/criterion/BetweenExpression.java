package com.sm.common.criterion;

import com.sm.common.persistence.helper.PersistenceHelperFacade;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Newbody on 2016/1/7.
 */
public class BetweenExpression implements Criterion {
    private final String propertyName;
    private final Object lo;
    private final Object hi;

    protected BetweenExpression(String propertyName, Object lo, Object hi) {
        this.propertyName = propertyName;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    public String toNamedSqlString() {
        return PersistenceHelperFacade.toColumnName(propertyName) + " between :lo_" + propertyName + " and :hi_" + propertyName;
    }

    @Override
    public Map<String, Object> getNamedParameterValues() {
        Map<String, Object> values = new HashMap<>();
        values.put("lo_" + propertyName, lo);
        values.put("hi_" + propertyName, hi);
        return values;
    }
}
