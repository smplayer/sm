package com.sm.user.persistence.persistenceHelper;

import com.sm.common.persistence.helper.BasicEntityPersistenceHelper;
import com.sm.user.entity.UserInfo;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Newbody on 12/3/2015.
 */
public class UserInfoPersistenceHelper extends BasicEntityPersistenceHelper<UserInfo> {
    @Override
    public Map<String, Object> toMap(UserInfo entity) {
        Map<String, Object> valueMap = super.toMap(entity);
        valueMap.put("nickname", entity.getNickname());
        valueMap.put("userId", entity.getUserId());
        return valueMap;
    }

    @Override
    public UserInfo fromMap(UserInfo entity, Map<String, Object> valueMap) {
        entity.setNickname((String) valueMap.get("nickname"));
        entity.setUserId((String) valueMap.get("userId"));
        return super.fromMap(entity, valueMap);
    }

    @Override
    public Collection<String> getPersistablePropertyNames() {
        Collection<String> names = super.getPersistablePropertyNames();
        names.add("nickname");
        names.add("userId");
        return names;
    }
}
