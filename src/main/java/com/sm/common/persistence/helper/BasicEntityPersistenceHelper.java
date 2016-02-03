package com.sm.common.persistence.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sm.common.entity.Entity;

import java.util.*;

/**
 * Created by Newbody on 12/2/2015.
 */
public class BasicEntityPersistenceHelper<E extends Entity> implements EntityPersistenceHelper<E> {
//    protected Class<?> entityClass;

    @Override
    public Map<String, Object> toMap(E entity) {
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("id", entity.getId());
        valueMap.put("lastUpdatedTime", entity.getLastUpdatedTime());
        valueMap.put("createdTime", entity.getCreatedTime());
        valueMap.put("status", entity.getStatus());
        return valueMap;
    }

//    @Override
//    public E fromMap(Class<E> entityClass, Map<String, Object> valueMap) {
//        E entity = null;
//        try {
//            entity = entityClass.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return fromMap(entity, valueMap);
//    }

    public E fromMap(E entity, Map<String, Object> valueMap) {
        entity.setId((String) valueMap.get("id"));
        entity.setLastUpdatedTime((Long) valueMap.get("lastUpdatedTime"));
        entity.setCreatedTime((Long) valueMap.get("createdTime"));
        entity.setStatus((String) valueMap.get("status"));
        return entity;
    }

    @Override
    public Collection<String> getPersistablePropertyNames() {
        return new ArrayList<>(Arrays.asList("id", "lastUpdatedTime", "createdTime", "status"));
    }

    /* json mapper */
    protected String toJsonString(Object o) {
        String jsonString = JSON.toJSONString(o);
        return jsonString;
    }

    protected Object fromJsonString(String jsonString, Class classType) {
        Object o = JSON.parseObject(jsonString, classType);
        return o;
    }

    protected Object fromJsonString(String jsonString, TypeReference typeReference) {
        Object o = JSON.parseObject(jsonString, typeReference);
        return o;
    }
}
