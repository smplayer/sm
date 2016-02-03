package com.sm.user.service;

import com.sm.common.R;
import com.sm.common.service.GenericService;
import com.sm.user.entity.User;

/**
 * Created by Newbody on 10/10/2015.
 */
public interface UserService extends GenericService<User, String> {
    Boolean existsUsername(String username);
}
