package com.zhaole.service.impl;

import com.zhaole.service.FollowService;
import com.zhaole.util.JedisAdapter;
import com.zhaole.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * created by zl on 2019/2/16 16:08
 */
@Service
public class FollowServiceImpl implements FollowService
{
    @Autowired
    JedisAdapter jedisAdapter;
    private static final Logger logger = LoggerFactory.getLogger(FollowServiceImpl.class);

    public boolean follow(int userId, int entityId, int entityType)
    {
        String followerKey = RedisKeyUtil.getFollowerKey(entityId,entityType);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);
        Date date = new Date();
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
        tx.zadd(followeeKey, date.getTime(), String.valueOf(entityId));
        List<Object> ret = jedisAdapter.exec(tx, jedis);
        logger.info("followServiceImpl:"+ret.size()+","+ret.get(0)+","+ret.get(1));
        return ret.size() == 2 && (Long)ret.get(0) > 0 && (Long)ret.get(1) > 0;
    }

    public boolean unfollow(int userId, int entityId, int entityType)
    {
        String followerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        //使用事务来实现关注和取消关注，事务能够确保同时实现
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        tx.zrem(followerKey, String.valueOf(userId));   //取消关注就是从zset中删除
        tx.zrem(followeeKey, String.valueOf(entityId));
        List<Object> ret = jedisAdapter.exec(tx, jedis);
        return ret.size() == 2 && (Long) ret.get(0) >0 && (Long) ret.get(1) > 0 ;
    }

    //辅助函数，从含有id的set转换成int
    public List<Integer> getIdFromSet(Set<String> idSet)
    {
        List<Integer> ids = new ArrayList<>();
        for(String str:idSet)
        {
            ids.add(Integer.parseInt(str));
        }
        return ids;
    }

    // 获取粉丝的列表
    public List<Integer> getFollowers(int entityId, int entityType,  int count)
    {
        String followerKey = RedisKeyUtil.getFollowerKey(entityId,entityType);
        return getIdFromSet(jedisAdapter.zrevrange(followerKey,0, count));
    }

    //带分页的粉丝列表的显示
    public List<Integer> getFollowers(int entityId, int entityType, int offset, int count)
    {
        String followerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);
        return getIdFromSet(jedisAdapter.zrevrange(followerKey, offset, offset+count));
    }

    // 获取我关注的实体的列表
    public List<Integer> getFollowees( int entityId, int entityType, int count)
    {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityId,entityType);
        return getIdFromSet(jedisAdapter.zrevrange(followeeKey,0, count));
    }

    //带分页的获取我关注的实体的列表
    public List<Integer> getFollowees(int entityId, int entityType, int offset, int count)
    {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityId, entityType);
        return getIdFromSet(jedisAdapter.zrevrange(followeeKey, offset, offset+count));
    }

    //统计粉丝的数量
    public long getFollowerCount(int entityId, int entityType)
    {
        String followerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);
        return jedisAdapter.zcard(followerKey);
    }

    //统计被关注对象的数量
    public long getFolloweeCount(int entityId, int entityType)
    {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityId,entityType);
        return jedisAdapter.zcard(followeeKey);
    }

    //判断粉丝是否在列表中
    public boolean isFollower(int userId, int entityId, int entityType)
    {
        String followerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);
        return jedisAdapter.zscore(followerKey, String.valueOf(userId)) != null;
    }
}


