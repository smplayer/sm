package com.sm.user.persistence.persistenceHelper;

import com.sm.common.persistence.helper.BasicEntityPersistenceHelper;
import com.sm.user.entity.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Newbody on 12/2/2015.
 */
public class UserPersistenceHelper extends BasicEntityPersistenceHelper<User> {

    @Override
    public Map<String, Object> toMap(User entity) {
        Map<String, Object> valueMap = super.toMap(entity);
        valueMap.put("username", entity.getUsername());
        valueMap.put("password", entity.getPassword());
        return valueMap;
    }

//    @Override
//    public User fromMap(Class<User> entityClass, Map<String, Object> valueMap) {
//        User user = new User();
//        user.setUsername((String) valueMap.get("username"));
//        user.setPassword((String) valueMap.get("password"));
//        return super.fromMap(user, valueMap);
//    }

    @Override
    public User fromMap(User entity, Map<String, Object> valueMap) {
        entity.setUsername((String) valueMap.get("username"));
        entity.setPassword((String) valueMap.get("password"));
        return super.fromMap(entity, valueMap);
    }

    @Override
    public Collection<String> getPersistablePropertyNames() {
        Collection<String> names = super.getPersistablePropertyNames();
        names.addAll(Arrays.asList("username", "password"));
        return names;
    }
}
