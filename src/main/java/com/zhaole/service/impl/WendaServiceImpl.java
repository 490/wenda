package com.zhaole.service.impl;

import com.zhaole.service.WendaService;
import org.springframework.stereotype.Service;

/**
 * created by zl on 2019/1/24 20:10
 */
@Service
public class WendaServiceImpl implements WendaService
{
    public String getMessage(int userId)

    {
        return "Hello Message: " + String.valueOf(userId);
    }
}
