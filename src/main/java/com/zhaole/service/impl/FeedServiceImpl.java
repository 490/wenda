package com.zhaole.service.impl;

import com.zhaole.dao.FeedDAO;
import com.zhaole.model.Feed;
import com.zhaole.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by zl on 2019/2/16 23:01
 */
@Service
public class FeedServiceImpl implements FeedService
{
    @Autowired
    FeedDAO feedDAO;

    //拉取的模式
    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count)
    {
        return feedDAO.selectUserFeeds(maxId, userIds, count);
    }
    public boolean addFeeds(Feed feed)
    {
        feedDAO.addFeed(feed);
        return feed.getId()>0;
    }
    //推的模式,就是把所有的消息推送给你的粉丝，这里存储的就是id,由id这个核心数据来代表新鲜事
    //这样的话就不用存储新鲜事的数据，在推送的时候的压力就会减小
    public Feed getById(int id)
    {
        return feedDAO.getFeedById(id);
    }

}
