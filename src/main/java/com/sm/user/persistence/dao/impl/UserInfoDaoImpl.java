package com.sm.user.persistence.dao.impl;

import com.sm.common.persistence.dao.impl.GenericDaoImpl;
import com.sm.user.entity.UserInfo;
import com.sm.user.persistence.dao.UserInfoDao;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Repository;

/**
 * Created by Newbody on 12/3/2015.
 */
@Repository("userInfoDao")
public class UserInfoDaoImpl extends GenericDaoImpl<UserInfo, String> implements UserInfoDao {

}
