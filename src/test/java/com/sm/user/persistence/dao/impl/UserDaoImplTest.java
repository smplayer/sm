package com.sm.user.persistence.dao.impl;

import com.sm.common.MessageDigestUtils;
import com.sm.user.UserCreationEvent;
import com.sm.user.persistence.dao.UserDao;
import com.sm.user.entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Newbody on 10/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:defaultDispatcher-servlet.xml", "classpath:applicationContext.xml"})
@TransactionConfiguration(defaultRollback = false)
public class UserDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Resource(name="userDao")
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setCreatedTime(new Date().getTime());
        user.setLastUpdatedTime(new Date().getTime());
        user.setUsername("newbody");
        user.setPassword(MessageDigestUtils.toMD5("newbody"));
        Boolean result = userDao.create(user);
        Assert.assertTrue(result);
    }

    @Test
    public void testModifyUser() throws Exception {

//        User user = new User();
//        user.setId("uuid123123123");
//        user.setUsername("newbody3");
//        user.setPassword(MessageDigestUtils.toMD5("newbody"));
//        user.setLastUpdatedTime(new Date().getTime());

        User user = userDao.retrieveById("uuid123123123");
        user.setUsername("newbody4");

        Boolean result = userDao.update(user);
        Assert.assertTrue(result);
    }

    @Test
    public void testExistsUsername() throws Exception {
        Assert.assertTrue(userDao.existsUsername("newbody"));
        Assert.assertFalse(userDao.existsUsername("aaa"));
    }
}