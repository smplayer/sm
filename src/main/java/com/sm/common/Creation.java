package com.sm.common;

import com.sm.common.entity.Entity;
import com.sm.common.persistence.helper.PersistenceHelperFacade;
import com.sm.user.entity.User;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Newbody on 2016/1/8.
 */
public class Creation {
    private Class<? extends Entity> entityClass;
    private Map<String, Object> valueMap;

    public Creation(Class<? extends Entity> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<? extends Entity> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<? extends Entity> entityClass) {
        this.entityClass = entityClass;
    }

    public Map<String, Object> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, Object> valueMap) {
        this.valueMap = valueMap;
    }

    public String toNamedSqlString() {
        StringBuilder builder = new StringBuilder("INSERT INTO ").append(PersistenceHelperFacade.toTableName(entityClass.getSimpleName()));
        builder.append("(");
        StringBuilder valuesBuilder = new StringBuilder("VALUES(");
        Iterator<String> propertyNameIterator = valueMap.keySet().iterator();
        while (propertyNameIterator.hasNext()) {
            String name = propertyNameIterator.next();
            builder.append(PersistenceHelperFacade.toColumnName(name));
            valuesBuilder.append(":").append(name);
            if (propertyNameIterator.hasNext()) {
                builder.append(",");
                valuesBuilder.append(",");
            }
        }
        valuesBuilder.append(")");
        builder.append(") ")
                .append(valuesBuilder);
        return builder.toString();
    }

    public Map<String, Object> getNamedParameterValues() {
        return valueMap;
    }

    public static void main(String[] args) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("username", "newbody");
        vm.put("status", "ok");
        Creation creation = new Creation(User.class);
        creation.setValueMap(vm);
        System.out.println(
                creation.toNamedSqlString()
        );
    }
}
