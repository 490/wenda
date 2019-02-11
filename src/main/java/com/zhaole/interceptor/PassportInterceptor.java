package com.zhaole.interceptor;

import com.zhaole.dao.LoginTicketDAO;
import com.zhaole.dao.UserDAO;
import com.zhaole.model.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by zl on 2019/1/25 18:56
 */
@Component
public class PassportInterceptor implements HandlerInterceptor
{
    @Autowired
    private LoginTicketDAO loginTicketDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletResponse httpServletResponse,
                             HttpServletRequest httpServletRequest, Object o)throws Exception
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
    }
}
