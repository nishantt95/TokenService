package com.cclogic.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;

/**
 * Created by Nishant on 9/26/2017.
 */
public class UserRepositoryImpl implements UserTokenRepository {

    private static final String KEY = "userToken";

    private RedisTemplate<String, String> redisTemplate;
    private HashOperations hashOps;

    @Autowired
    private UserRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }


    @Override
    public void saveUserToken(UserToken userToken) {
        hashOps.put(KEY,userToken.getId(),userToken.getRefreshToken());
    }

    @Override
    public UserToken getUserTokenFromId(long id) {
        return null;
    }
}
