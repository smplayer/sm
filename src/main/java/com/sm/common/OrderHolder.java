package com.sm.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Newbody on 2016/1/7.
 */
public class OrderHolder {
    private List<Order> restrictions = new ArrayList<>();

    public OrderHolder add(Order restriction) {
        this.restrictions.add(restriction);
        return this;
    }

    public String toSqlString() {
        Iterator<Order> iterator = restrictions.iterator();
        StringBuilder builder = new StringBuilder();
        if (iterator.hasNext()) {
            builder.append(" ORDER BY ");
        }
        while (iterator.hasNext()) {
            Order restriction = iterator.next();
            builder.append(restriction.getPropertyName()).append(" ").append(restriction.getOrderMode().getValue());
            if (iterator.hasNext()) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
