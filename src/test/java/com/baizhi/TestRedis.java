package com.baizhi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis(){

        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name","zhangcn");
        redisTemplate.expire("name",10, TimeUnit.SECONDS);

        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();

        //valueOperations.set("name","xiaohei");
        String name = (String)valueOperations.get("name");
        //System.out.println(name);
        Set keys = redisTemplate.keys("*");
        for (Object key : keys) {
            System.out.println(key);
        }
    }

    @Test
    public void testRediss(){

        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name","zhangcn");
        redisTemplate.expire("name",30, TimeUnit.SECONDS);

    }

    @Test
    public void testRedisss(){

        ValueOperations valueOperations = redisTemplate.opsForValue();
        String name = (String)valueOperations.get("name");
        System.out.println(name);

    }

    @Test
    public void testRedissss(){

        ValueOperations valueOperations = redisTemplate.opsForValue();

        Boolean namw = redisTemplate.delete("namw");
        System.out.println(namw);


    }

}
