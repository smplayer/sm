package com.sm.user.service.impl;

import com.sm.common.MessageDigestUtils;
import com.sm.common.R;
import com.sm.user.entity.User;
import com.sm.user.entity.UserInfo;
import com.sm.user.service.UserFacade;
import com.sm.user.service.UserInfoService;
import com.sm.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Newbody on 12/3/2015.
 */
@Service("userFacade")
public class UserFacadeImpl implements UserFacade {
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "userInfoService")
    private UserInfoService userInfoService;

    @Override
    public R<Boolean> register(String username, String password) {
        R<Boolean> r = new R<>();
        Boolean existsUsername = userService.existsUsername(username);
        if (existsUsername) {
            r.addData(MESSAGE, USERNAME_DUPLICATE);
            r.setResult(false);
            return r;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(MessageDigestUtils.toMD5(password));
        userService.create(user);

        UserInfo info = new UserInfo();
        info.setNickname(user.getUsername());
        info.setUserId(user.getId());
        userInfoService.create(info);

        r.setResult(true);
        r.addData("user", user);
        return r;
    }

    @Override
    public R<Boolean> login(String username, String password) {
        R<Boolean> r = new R<>(false);
        if (userService.existsUsername(username)) {
            User user = userService.get("username", username);
            String passwordToMD5 = MessageDigestUtils.toMD5(password);
            Boolean result = passwordToMD5.equals(user.getPassword());
            if(result){r.setResult(true);
                r.addData("user", user);
            } else {
                r.addData(MESSAGE, NOT_MATCH);
            }
        } else {
            r.addData(MESSAGE, USERNAME_NOT_EXIST);
        }
        return r;
    }
}
