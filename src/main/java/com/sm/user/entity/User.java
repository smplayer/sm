package com.sm.user.entity;

import com.sm.common.entity.Entity;

/**
 * Created by Newbody on 10/10/2015.
 */
public class User extends Entity {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
