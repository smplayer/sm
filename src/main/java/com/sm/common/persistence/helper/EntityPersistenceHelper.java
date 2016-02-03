package com.sm.common.persistence.helper;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Newbody on 12/2/2015.
 */
public interface EntityPersistenceHelper<E> {
    Map<String, Object> toMap(E entity);
//    E fromMap(Class<E> entityClass, Map<String, Object> valueMap);
    E fromMap(E entity, Map<String, Object> valueMap);
    Collection<String> getPersistablePropertyNames();
//    void init(E entity);
//    void refreshUpdatedTime(E entity);
}
