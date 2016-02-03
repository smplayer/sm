package com.sm.common.criterion;

/**
 * Created by Newbody on 2016/1/8.
 */
public class CountProjection extends AggregateProjection {
    private boolean distinct;
    protected CountProjection(String prop) {
        super( "count", prop );
    }
    public CountProjection setDistinct() {
        distinct = true;
        return this;
    }
}
