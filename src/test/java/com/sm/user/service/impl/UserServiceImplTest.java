package com.sm.user.service.impl;

import com.sm.user.UserCreationEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static org.junit.Assert.*;

/**
 * Created by Newbody on 2016/1/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:defaultDispatcher-servlet.xml", "classpath:applicationContext.xml"})
@TransactionConfiguration(defaultRollback = false)
public class UserServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Test
    public void testPublishEvent() {
        applicationContext.publishEvent(new UserCreationEvent("新用户创建"));
    }

    @Before
    public void setUp() throws Exception {

    }
}