package com.zhaole.interceptor;

import com.zhaole.dao.LoginTicketDAO;
import com.zhaole.dao.UserDAO;
import com.zhaole.model.HostHolder;
import com.zhaole.model.LoginTicket;
import com.zhaole.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PasswordInterceptor implements HandlerInterceptor
{
    @Autowired
    private LoginTicketDAO loginTicketDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse,
                             Object o) throws Exception
    {
        String ticket = null;
        if(httpServletRequest.getCookies()!=null)
        {
            for(Cookie cookie:httpServletRequest.getCookies())
            {
                if(cookie.getName().equals("ticket"))
                {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if(ticket!=null)//说明cookie不空，且name是ticket
        {
            //去数据库里把ticket找出来看看是否有效
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            if(loginTicket==null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus()!=0)
            {
                return true;
            }
            //有效的话，查这个ticket对应的用户，把这个用户设置到threadlocal里。
            User user = userDAO.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
