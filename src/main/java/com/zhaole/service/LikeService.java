package com.zhaole.service;

import com.zhaole.util.JedisAdapter;
import com.zhaole.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nowcoder on 2016/7/30.
 */
public interface LikeService {



    public long getLikeCount(int entityType, int entityId) ;

    public int getLikeStatus(int userId, int entityType, int entityId);

    public long like(int userId, int entityType, int entityId);

    public long disLike(int userId, int entityType, int entityId) ;
}
