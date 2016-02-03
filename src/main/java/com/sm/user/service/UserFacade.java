package com.sm.user.service;

import com.sm.common.R;

/**
 * Created by Newbody on 12/3/2015.
 */
public interface UserFacade {
    public static String USERNAME_DUPLICATE = "username_duplicate";
    public static String USERNAME_TOO_LONG = "username_too_long";
    public static String USERNAME_TOO_SHORT = "username_too_short";
    public static String USERNAME_NOT_EXIST = "username_too_short";
    public static String PASSWORD_TOO_SHORT = "password_too_short";
    public static String ILLEGAL_CHARACTERS = "illegal_characters";
    public static String NOT_MATCH = "not_match";
    public static String MESSAGE = "message";
    R<Boolean> register(String username, String password);
    R<Boolean> login(String username, String password);
}
