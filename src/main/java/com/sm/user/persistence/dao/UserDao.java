package com.sm.user.persistence.dao;

import com.sm.common.persistence.dao.GenericDao;
import com.sm.user.entity.User;

/**
 * Created by Newbody on 10/10/2015.
 */
public interface UserDao extends GenericDao<User, String> {
//    Boolean createUser(User user);
//    User retrieveUser(String userId);
    User retrieveUserByUsername(String username);
//    Boolean modifyUser(User user);
//    Boolean existsUserId(String userId);
    Boolean existsUsername(String username);
}
