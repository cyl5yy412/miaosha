package com.lnsoft;

import com.lnsoft.mapper.CartMapper;
import com.lnsoft.test.redis.RedisTest;
import com.lnsoft.test.redis.RedisUtilImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
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
    public void get() {
        for (int x = 0; x <= 10; x++) {
            List<String> strings = redisTest.get();
        }
    }

    @Resource
    private CartMapper cartMapper;

    @Test
    public void show() {

        List<Map<String, String>> chr = cartMapper.queryLike("ch");
        chr.forEach(testChr -> {
            System.out.println(testChr);
//            testChr.forEach((k, v) -> {
//                System.out.println(k + "==" + v);
                System.out.println(StringUtils.join(testChr.keySet().toArray()));
                System.out.println(testChr.values().toArray());
//            });
        });
    }

    @Test
   public void show3(){
        System.err.println(HttpStatus.OK);

    }

    @Test
    public void show2() {
       //HASH
//        stringRedisTemplate.opsForHash().put("Chr2", "23456780", "张三");
//        Object chr = stringRedisTemplate.opsForHash().get("Chr2", "23456780");
//        System.out.println(chr);


        //LIST
        //stringRedisTemplate.opsForList().rightPush("Chr3—list","111111");
       // stringRedisTemplate.opsForList().leftPush("Chr3—list","11111123");
       //pop左边最前一个，并且删除，类似于mq
//        String leftPop = stringRedisTemplate.opsForList().rightPop("Chr3—list");
//        stringRedisTemplate.opsForList().leftPush("Chr3—list","asd3r");
//        System.out.println(leftPop);
        //stringRedisTemplate.opsForList().leftPush("Chr3—list","82323997");


//SET
//        stringRedisTemplate.opsForSet().add("Chr4","abc435");
//        stringRedisTemplate.opsForSet().add("Chr4","abc4");


        //ZSET
        //stringRedisTemplate.opsForZSet().add("Chr-Zset", "小明", 352);
      //  stringRedisTemplate.opsForZSet().add("Chr-Zset", "小张", 32);
        //stringRedisTemplate.opsForZSet().add("Chr-Zset", "小马", 31);
       // stringRedisTemplate.opsForZSet().add("Chr-Zset", "小孙", 32);
       stringRedisTemplate.opsForZSet().add("Chr-Zset", "小迟", 32.556);
    }

}
