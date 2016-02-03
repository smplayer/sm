package com.sm.user.service.impl;

import com.sm.common.persistence.dao.GenericDao;
import com.sm.common.service.impl.GenericServiceImpl;
import com.sm.user.entity.UserInfo;
import com.sm.user.persistence.dao.UserInfoDao;
import com.sm.user.service.UserInfoService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by Newbody on 12/2/2015.
 */
@Repository("userInfoService")
public class UserInfoServiceImpl extends GenericServiceImpl<UserInfo, String> implements UserInfoService {
    @Resource(name = "userInfoDao")
    private UserInfoDao userInfoDao;

    public UserInfoServiceImpl(){}

    @Override
    protected GenericDao getDao() {
        return userInfoDao;
    }
}
