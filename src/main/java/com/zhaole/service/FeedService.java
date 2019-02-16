package com.zhaole.service;

import com.zhaole.model.Feed;

import java.util.List;

/**
 * created by zl on 2019/2/16 22:45
 */
public interface FeedService
{
    public List<Feed> getUserFeeds (int max_id, List<Integer> user_ids, int count);
    public boolean addFeeds(Feed feed);
    public Feed getById(int id);
}
