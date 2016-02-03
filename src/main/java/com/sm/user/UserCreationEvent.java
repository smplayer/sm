package com.sm.user;

import org.springframework.context.ApplicationEvent;

/**
 * Created by Newbody on 2016/1/12.
 */
public class UserCreationEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public UserCreationEvent(Object source) {
        super(source);
    }
}
