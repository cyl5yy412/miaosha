package com.lnsoft;

import com.lnsoft.test.redis.RedisTest;
import com.lnsoft.test.redis.RedisUtilImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);
    @Autowired
    private RedisUtilImpl redisUtill;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
//        assertTrue(true);
        getAccountByName("80808");
    }

    //    @CachePut(value="11111",key="#telPhone")
    public String getAccountByName(String telPhone) {
        // 方法内部实现不考虑缓存逻辑，直接实现业务
        System.out.println("real query account." + telPhone);
//        String s = stringRedisTemplate.opsForValue().get(telPhone);
//        System.err.println(s);
        stringRedisTemplate.opsForValue().set("32100", "3210000", 6000, TimeUnit.SECONDS);
        return "80808";

    }

    //测试第一次去数据库查，第二次去缓存
    @Autowired
    private RedisTest redisTest;
    @Test
    public void get(){
        for (int x=0;x<=10;x++){
            List<String> strings = redisTest.get();
        }
    }
}
