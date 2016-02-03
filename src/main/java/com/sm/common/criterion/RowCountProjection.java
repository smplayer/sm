package com.sm.common.criterion;

/**
 * Created by Newbody on 2016/1/8.
 */
public class RowCountProjection implements Projection {
    @Override
    public String toSqlString() {
        return "count(*)";
    }
}
