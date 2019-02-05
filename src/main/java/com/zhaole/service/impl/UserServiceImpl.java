package com.zhaole.service.impl;

import com.zhaole.dao.LoginTicketDAO;
import com.zhaole.dao.UserDAO;
import com.zhaole.model.LoginTicket;
import com.zhaole.model.User;
import com.zhaole.service.UserService;
import com.zhaole.util.WendaUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * created by zl on 2019/1/24 21:39
 */
@Service
public class UserServiceImpl implements UserService
{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;
    public User getUser(int id)
    {
        return userDAO.selectById(id);
    }

    @Override
    public Map<String, Object> register(String username, String password) {
        Map<String ,Object> map = new HashMap<String,Object>();
        if(StringUtils.isBlank(username))
        {
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password))
        {
            map.put("msg","密码不能为空");
            return map;
        }
        User user = userDAO.selectByName(username);
        if(user != null)
        {
            map.put("msg","用户名已被注册");
            return map;
        }

        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }
    //返回一个ticket(string)的map
    public Map<String,Object> login(String username, String password)
    {
        Map<String,Object> map = new HashMap<String,Object>();
        if(StringUtils.isBlank(username))
        {
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password))
        {
            map.put("msg","密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);
        if(user==null)
        {
            map.put("msg","用户名不存在");
            return map;
        }
        if(!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword()))
        {
            map.put("msg","密码不正确");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public String addLoginTicket(int userId)
    {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }
    public void logout(String ticket)
    {
        loginTicketDAO.updateStatus(ticket,1);
    }

    public User selectByName(String name) {
        return userDAO.selectByName(name);
    }
}
