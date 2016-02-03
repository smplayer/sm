package com.sm.common.persistence.helper;

import com.sm.common.entity.Entity;
import com.sm.user.entity.User;
import com.sm.user.entity.UserInfo;
import com.sm.user.persistence.persistenceHelper.UserInfoPersistenceHelper;
import com.sm.user.persistence.persistenceHelper.UserPersistenceHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Newbody on 12/2/2015.
 */
public class PersistenceHelperFacade {
    private static Map<Class, Class> helperClassMap = new HashMap<>();

    static {
        helperClassMap.put(User.class, UserPersistenceHelper.class);
        helperClassMap.put(UserInfo.class, UserInfoPersistenceHelper.class);
    }

    private static Map<Class, EntityPersistenceHelper> helperMap = new HashMap<>();

    private static EntityPersistenceHelper getHelper(Class entityClass) {
        EntityPersistenceHelper helper = helperMap.get(entityClass);
        if (helper == null) {
            try {
                helper = (EntityPersistenceHelper) helperClassMap.get(entityClass).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        helperMap.put(entityClass, helper);
        return helper;
    }

    public static void initEntityId(Entity entity){
        entity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    public static Map<String, Object> toMap(Object entity) {
        return getHelper(entity.getClass()).toMap(entity);
    }

    public static <E> E fromMap(Class<E> entityClass, Map<String, Object> valueMap) {
        E entity = null;
        try {
            entity = entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (E) getHelper(entityClass).fromMap(entity, valueMap);
    }

    public static <E> E fromMap(E entity, Map<String, Object> valueMap) {
        return (E) getHelper(entity.getClass()).fromMap(entity, valueMap);
    }

    public static String toTableName(String entityName){
        return "tb_" + entityName;
    }

    public static String toColumnName(String propertyName){
        return "c_" + propertyName;
    }

    public static Collection<String> getPersistablePropertyNames(Class entityClass) {
        return getHelper(entityClass).getPersistablePropertyNames();
    }

    public static void main(String[] args) {
        System.out.println(PersistenceHelperFacade.toMap(User.class));
    }
}
