package com.zhaole.controller;

import com.zhaole.WendaApplication;
import com.zhaole.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * created by zl on 2019/1/24 15:57
 */
@Controller
public class SettingController
{
    @Autowired
    WendaService wendaService;

    @RequestMapping(path = {"/setting"}, method = {RequestMethod.GET})
    @ResponseBody
    public String setting(HttpSession httpSession)
    {
        return "Setting ok. " + wendaService.getMessage(1);
    }
}
