package com.sm.common.service;

import com.sm.common.entity.Entity;

import java.util.Collection;

/**
 * Created by Newbody on 11/24/2015.
 */
public interface BasicService {
    public void preCreate(Entity entity);
    public void preCreate(Collection entities);
    public void preModify(Entity entity);
}
