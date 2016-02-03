package com.sm.common.service.impl;

import com.sm.common.entity.Entity;
import com.sm.common.service.BasicService;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Newbody on 10/10/2015.
 */
public class BasicServiceImpl implements BasicService {
    public void preCreate(Entity entity) {
        if (StringUtils.isBlank(entity.getId()))
            entity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        entity.setCreatedTime(new Date().getTime());
        entity.setLastUpdatedTime(new Date().getTime());
    }

    public void preCreate(Collection entities) {
        for (Object e : entities) {
            preCreate((Entity) e);
        }
    }

    public void preModify(Entity entity) {
        entity.setLastUpdatedTime(new Date().getTime());
    }
}
