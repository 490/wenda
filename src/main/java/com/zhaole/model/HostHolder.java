package com.zhaole.model;

import org.springframework.stereotype.Component;

/**
 * created by zl on 2019/1/25 9:01
 */
@Component
public class HostHolder
{
    //本地线程变量，在每个线程中都有一份该变量的拷贝，因此有多个线程同时访问的时候也不会发生冲突
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
