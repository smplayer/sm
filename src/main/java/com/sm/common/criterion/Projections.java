package com.sm.common.criterion;

/**
 * Created by Newbody on 2016/1/8.
 */
public class Projections {
    private Projections() {
        //cannot be instantiated
    }

    public static Projection rowCount() {
        return new RowCountProjection();
    }
    public static CountProjection count(String propertyName) {
        return new CountProjection( propertyName );
    }
    public static CountProjection countDistinct(String propertyName) {
        return new CountProjection( propertyName ).setDistinct();
    }
    public static AggregateProjection max(String propertyName) {
        return new AggregateProjection( "max", propertyName );
    }
    public static AggregateProjection min(String propertyName) {
        return new AggregateProjection( "min", propertyName );
    }
    public static AggregateProjection avg(String propertyName) {
        return new AggregateProjection( "avg", propertyName );
    }
    public static AggregateProjection sum(String propertyName) {
        return new AggregateProjection( "sum", propertyName );
    }
}
