package com.lnsoft.test.redis;

/**
 * Created By Chr on 2019/2/22/0022.
 */
public interface RedisUtil {
    //设置，序列化
    boolean set(String key, String value);

    //得到，反序列化
    String get(String key);

    //过期时间
    void expire(String key, long time);
}
