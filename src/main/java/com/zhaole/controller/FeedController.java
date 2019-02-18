package com.zhaole.controller;

import com.zhaole.model.EntityType;
import com.zhaole.model.Feed;
import com.zhaole.model.HostHolder;
import com.zhaole.service.FeedService;
import com.zhaole.service.FollowService;
import com.zhaole.util.JedisAdapter;
import com.zhaole.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(path = {"/pullfeeds"},method = {RequestMethod.GET})
    private String getPullFeeds(Model model)
    {
        int localUserId = hostHolder.getUser() == null ? 0 : hostHolder.getUser().getId();
        List<Integer> followees = new ArrayList<>();
        if(localUserId !=0)
        {
            //找到所有我关注的人
            followees = followService.getFollowees(localUserId, EntityType.ENTITY_USER, Integer.MAX_VALUE);
        }
        List<Feed> feeds = feedService.getUserFeeds(Integer.MAX_VALUE, followees, 10);
        model.addAttribute("feeds", feeds);
        return "feeds";
    }

    @RequestMapping(path = {"/pushfeeds"},method = {RequestMethod.GET,RequestMethod.POST})
    private String getPushFeeds(Model model)
    {
        int localUserId = hostHolder.getUser()==null ? 0 : hostHolder.getUser().getId();
        //从这个redis中把ID取出来就可以了 之前在Feedhandler中的doHandle已经推给我了，推进去了然后现在取出来就可以了
        List<String> feedsId = jedisAdapter.lrange(RedisKeyUtil.getTimelineKey(localUserId),0,10);
        List<Feed> feeds = new ArrayList<>();
        for(String feedId : feedsId)
        {
            Feed feed = feedService.getById(Integer.parseInt(feedId));
            if(feed == null)
            {
                continue;
            }
            feeds.add(feed);
        }
        model.addAttribute("feeds", feeds);
        return "feeds";
    }
}
