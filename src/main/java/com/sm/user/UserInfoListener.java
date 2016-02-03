package com.sm.user;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by Newbody on 2016/1/12.
 */
@Component
public class UserInfoListener implements ApplicationListener<UserCreationEvent> {

    @Async
    @Override
    public void onApplicationEvent(UserCreationEvent event) {
        System.out.println(event.getSource().toString());
    }

}
