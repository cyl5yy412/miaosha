package com.lnsoft.test.redis;

import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By Chr on 2019/2/22/0022.
 */
@Component
public class RedisTest {

    @Autowired
    private RedisUtilImpl redisUtill;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);


    public void show() {
        stringRedisTemplate.opsForHash().put("Chr", "234", "YuLin");
        Object chr = stringRedisTemplate.opsForHash().get("chr", "234");
        System.out.println(chr);
    }


    public List<String> get() {
        String SS = "123345";
        List<String> strings = new ArrayList<String>();
        String s = redisUtill.get(SS);
        if (null != s) {
            logger.info("cache value-----------------------------------", s);
            strings = JSONArray.parseArray(s, String.class);
            return strings;
        }
        //模拟数据库查处的值
        strings.add("123345");
        strings.add("1233456");
        logger.info("db value-----------------------------------", strings);
        //存到缓存
        redisUtill.set(SS, JSONArray.toJSONString(strings));
        redisUtill.expire(SS, 120);
        return strings;
    }
}
