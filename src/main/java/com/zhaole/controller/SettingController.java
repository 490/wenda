package com.zhaole.controller;

import com.zhaole.WendaApplication;
import com.zhaole.service.WendaService;
import com.zhaole.util.RateLimit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(SettingController.class);

    @Autowired
    WendaService wendaService;

    @RateLimit(limitNum = 10)
    @RequestMapping(path = {"/setting"}, method = {RequestMethod.GET})
    @ResponseBody
    public String setting(HttpSession httpSession)
    {
        logger.info("调用 setting");
        return "Setting ok. " + wendaService.getMessage(1);
    }
}
