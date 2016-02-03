package com.sm.common.criterion;

import java.util.Map;

/**
 * Created by Newbody on 2016/1/7.
 */
public interface Criterion {
    public String toNamedSqlString();
    public Map<String, Object> getNamedParameterValues();
}
