package com.zhaole.service.impl;

import com.zhaole.dao.UserDAO;
import com.zhaole.model.User;
import com.zhaole.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by zl on 2019/1/24 21:39
 */
@Service
public class UserServiceImpl implements UserService
{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDAO userDAO;
    public User getUser(int id)
    {
        return userDAO.selectById(id);
    }
}
