package com.koko.config.shiro.cache;

import com.koko.config.shiro.cache.CustomCache;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

public class CustomCacheManager implements CacheManager {
    private RedisTemplate<String, Object> redisTemplate;

    public CustomCacheManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new CustomCache<K, V>(redisTemplate);
    }
}
