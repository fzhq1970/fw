package com.ccb.ha.fw.base.cache;

import com.ccb.ha.fw.base.config.RedisOperator;
import org.springframework.stereotype.Component;

@Component
public class CacheObject{
    final RedisOperator redisOperator;

    public CacheObject(RedisOperator redisOperator) {
        this.redisOperator = redisOperator;
    }

    public void put(String key, String data) {
        //默认120分钟
        this.put(key, data, 120L);
    }

    public void put(String key, String data, Long exp) {
        this.redisOperator.set(key, data, exp);
    }

    public String remove(String key) {
        String value = this.redisOperator.get(key);
        if (value != null) {
            this.redisOperator.del(key);
        }
        return value;
    }

    public String get(String key) {
        return this.redisOperator.get(key);
    }

    public boolean valid(String key) {
        return (this.redisOperator.get(key) != null);
    }
}
