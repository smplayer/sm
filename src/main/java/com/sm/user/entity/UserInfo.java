package com.sm.user.entity;

import com.sm.common.entity.Entity;

/**
 * Created by Newbody on 12/3/2015.
 */
public class UserInfo extends Entity {
    private String nickname;
    private String userId;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
