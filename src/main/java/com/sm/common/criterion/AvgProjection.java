package com.sm.common.criterion;

/**
 * Created by Newbody on 2016/1/8.
 */
public class AvgProjection extends AggregateProjection {
    public AvgProjection(String propertyName) {
        super( "avg", propertyName );
    }
}
