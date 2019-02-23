package com.zhaole.service.impl;

import com.zhaole.service.LikeService;
import com.zhaole.util.JedisAdapter;
import com.zhaole.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class LikeServiceImpl implements LikeService
{
    @Autowired
    JedisAdapter jedisAdapter;


    public long getLikeCount(int entityType, int entityId)
    {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        return jedisAdapter.scard(likeKey);
    }

    public int getLikeStatus(int userId, int entityType, int entityId)
    {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if (jedisAdapter.sismember(likeKey, String.valueOf(userId)))
        {
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        return jedisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
    }
    //返回like数量
    public long like(int userId, int entityType, int entityId)
    {
        //like:entitytype:entityid
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        //<like:entitytype:entityid , userid>
        //System.out.println("likeKey::"+likeKey+",,,userid:::"+userId);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }

    public long disLike(int userId, int entityType, int entityId)
    {
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.srem(likeKey, String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }
}
