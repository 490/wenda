package com.zhaole.controller;

import com.zhaole.model.HostHolder;
import com.zhaole.service.FeedService;
import com.zhaole.service.FollowService;
import com.zhaole.util.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * created by zl on 2019/2/16 23:40
 */
@Controller
public class FeedController
{
    @Autowired
    FeedService feedService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    FollowService followService;
    @Autowired
    JedisAdapter jedisAdapter;
}
