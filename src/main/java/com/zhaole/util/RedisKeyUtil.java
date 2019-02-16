package com.zhaole.util;

/**
 *
 */
public class RedisKeyUtil
{
    private static String SPLIT = ":";
    private static String LIKE = "LIKE";
    private static String DISLIKE = "DISLIKE";
    private static String EVENTQUEUE = "EVENT_QUEUE";

    private static String FOLLOWER = "FOLLOWER";
    private static String FOLLOWEE = "FOLLOWEE";
    private static String TIMELINE = "TIMELINE";

    //获取点赞的key
    public static String getLikeKey(int entityType, int entityId)
    {
        return LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityType, int entityId)
    {
        return DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getEventQueueKey()
    {
        return EVENTQUEUE;
    }
    //获取每一个实体（问题或者是人）的粉丝对象的key
    public static String getFollowerKey(int entityId, int entityType)
    {
        return FOLLOWER + SPLIT + String.valueOf(entityId) + SPLIT + String.valueOf(entityType);
    }

    //获取关注的实体的key也就是某个人关注的某一类问题
    public static String getFolloweeKey(int userId, int entityType)
    {
        return FOLLOWEE + SPLIT + String.valueOf(userId) + SPLIT + String.valueOf(entityType);
    }

    //获取TimeLine的key
    public static String getTimelineKey(int userId)
    {
        return TIMELINE + SPLIT + String.valueOf(userId);
    }
}
