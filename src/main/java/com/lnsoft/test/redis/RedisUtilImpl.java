package com.lnsoft.test.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 测试redis，跟project无关
 * Created By Chr on 2019/2/22/0022.
 */
@Component
public class RedisUtilImpl implements RedisUtil {

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    //序列化
    @Override
    public boolean set(String key, String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                //序列化器
                RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
                //序列化key，value
                connection.set(stringSerializer.serialize(key), stringSerializer.serialize(value));
                return true;
            }
        });
    }

    //反序列化
    @Override
    public String get(String key) {
        return redisTemplate.execute(new RedisCallback<String>() {

            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                //序列化器
                RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
                //反序列化key中的value
                byte[] bytes = connection.get(stringSerializer.serialize(key));
                return stringSerializer.deserialize(bytes);
            }
        });
    }

    @Override
    public void expire(String key, long time) {

        //秒
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }
}
