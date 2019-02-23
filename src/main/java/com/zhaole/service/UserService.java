package com.zhaole.service;

import com.zhaole.model.User;

import java.util.Map;

/**
 * created by zl on 2019/1/24 21:39
 */
public interface UserService
{
    public User getUser(int id);
    public Map<String, Object> register(String username,String password);
    public Map<String, Object> login(String username,String password);
    public String addLoginTicket(int userId);
    public void logout(String ticket);
    public User selectByName(String name);
}
