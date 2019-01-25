package com.zhaole.model;

import org.springframework.stereotype.Component;

/**
 * created by zl on 2019/1/25 9:01
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<User>();
    public User getUser()
    {
        return users.get();
    }
    public void setUser(User user)
    {
        users.set(user);
    }
    public void clear()
    {
        users.remove();
    }
}
