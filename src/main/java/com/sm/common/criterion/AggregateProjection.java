package com.sm.common.criterion;

import com.sm.common.persistence.helper.PersistenceHelperFacade;

/**
 * Created by Newbody on 2016/1/8.
 */
public class AggregateProjection implements Projection {
    protected final String propertyName;
    private final String functionName;

    protected AggregateProjection(String functionName, String propertyName) {
        this.functionName = functionName;
        this.propertyName = propertyName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getPropertyName() {
        return propertyName;
    }


    @Override
    public String toSqlString() {
        return functionName + "(" + PersistenceHelperFacade.toColumnName(propertyName) + ")";
    }

}
