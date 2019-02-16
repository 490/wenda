package com.zhaole.service;

import java.util.List;
import java.util.Set;

/**
 * created by zl on 2019/2/16 16:06
 */
public interface FollowService
{
    public boolean follow(int userId, int entityId, int entityType);
    public boolean unfollow(int userId, int entityId, int entityType);
    public List<Integer> getIdFromSet(Set<String> idSet);
    public List<Integer> getFollowers(int entityId, int entityType,  int count);
    public List<Integer> getFollowers(int entityId, int entityType, int offset, int count);
    public List<Integer> getFollowees( int entityId, int entityType, int count);
    public List<Integer> getFollowees(int entityId, int entityType, int offset, int count);
    public long getFollowerCount(int entityId, int entityType);
    public long getFolloweeCount(int entityId, int entityType);
    public boolean isFollower(int userId, int entityId, int entityType);
}
