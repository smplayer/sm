package com.sm.user.service.impl;

import com.sm.common.MessageDigestUtils;
import com.sm.common.R;
import com.sm.common.persistence.dao.GenericDao;
import com.sm.common.service.GenericService;
import com.sm.common.service.impl.BasicServiceImpl;
import com.sm.common.service.impl.GenericServiceImpl;
import com.sm.user.persistence.dao.UserDao;
import com.sm.user.entity.User;
import com.sm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Newbody on 10/10/2015.
 */
@Service("userService")
public class UserServiceImpl extends GenericServiceImpl<User, String> implements UserService {
    @Autowired
    private ApplicationContext applicationContext;

    @Resource(name = "userDao")
    private UserDao userDao;

    @Override
    protected GenericDao<User, String> getDao() {
        return userDao;
    }

    @Override
    public Boolean existsUsername(String username) {
        return userDao.existsUsername(username);
    }

}
