package com.sm.common.persistence.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Newbody on 10/10/2015.
 */
public interface BasicDao {
    public Boolean exists(String entityName, String propertyName, String value);
    public Long count(String entityName, Map<String, Object> params);
    public Long count(String entityName);
    public Object min(String entityName, String columnName, Map<String, Object> constrained);
    public Object max(String entityName, String columnName, Map<String, Object> constrained);
}
