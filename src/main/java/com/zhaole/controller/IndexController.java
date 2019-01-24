package com.zhaole.controller;

import com.zhaole.model.User;
import com.zhaole.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * created by zl on 2019/1/24 16:19
 */

@Controller
public class IndexController
{
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    WendaService wendaService;

    @RequestMapping(path = {"/","/index"},method = {RequestMethod.GET})
    @ResponseBody
    public String index(HttpSession httpSession)
    {
        logger.info("visit home");
        return wendaService.getMessage(2) + "hello nowcoder " + httpSession.getAttribute("msg");
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public  String profile(@PathVariable("userId") int userId,
                           @PathVariable("groupId") int groupId,
                           @RequestParam(value = "type", defaultValue = "1") int type,
                           @RequestParam(value = "key", required = false) String key)
    {
        return String.format("profile page of %s / %d, t:%d k:%s",groupId,userId,type,key);
    }

    @RequestMapping(path = {"/vm"},method = RequestMethod.GET)
    public String template(Model model)
    {
        model.addAttribute("value1","v1");
        List<String> colors = Arrays.asList(new String[]{"red","green","blue"});
        model.addAttribute("colors",colors);

        Map<String,String> map = new HashMap<>();
        for(int i = 0; i < 4;i++)
        {
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("map",map);
        model.addAttribute("user",new User("zl"));
        return "home";
    }

    @RequestMapping(path = {"/redirect/{code}"},method = {RequestMethod.GET})
    public RedirectView redirect(@PathVariable("code") int code,
                                 HttpSession httpSession)
    {
        httpSession.setAttribute("msg","jump from redirect");
        RedirectView redirectView = new RedirectView("/",true);
        if(code == 301)
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return redirectView;
    }

    @RequestMapping(path = {"/admin"},method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key)
    {
        if("admin".equals(key))
        {
            return "hello admin";
        }
        throw new IllegalArgumentException("参数错误");
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e)
    {
        return "error : " + e.getMessage();
    }

}
