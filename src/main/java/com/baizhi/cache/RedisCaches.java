package com.baizhi.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.Set;

@Configuration
@Aspect
public class RedisCaches {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate StringRedisTemplate;

    @Around("@annotation(com.baizhi.annotation.AddCache)")
    public Object around (ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("====环绕通知====");

        /*解决缓存乱码*/
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

            //创建一个可边长字符串
            StringBuilder sb = new StringBuilder();

            //设置key  value
            //类的全限定名+方法名+实参
            //获取类的全限定名
            String clazzName = proceedingJoinPoint.getTarget().getClass().getName();
            sb.append(clazzName);

            //获取方法名
            String methodName = proceedingJoinPoint.getSignature().getName();
            sb.append(methodName);

            //获取实参 数组
            Object[] args = proceedingJoinPoint.getArgs();
            for (Object arg : args) {
                sb.append(arg);
            }

            //获取可变长字符串
            String key = sb.toString();

            //String类型的操作
            ValueOperations valueOperations = redisTemplate.opsForValue();

            //判断缓存中是否有数据  根据key判断
            Boolean aBooleanKey = redisTemplate.hasKey(key);
            System.out.println(aBooleanKey);

            //结果
            Object result = null;

            //判断key是否存在
            if (aBooleanKey) {
                //key存在
                //有数据  查询数据  放行
                result = valueOperations.get(key);

            } else {
                //key不存在
                //获取数据
                result = proceedingJoinPoint.proceed();

                //没有数据  将数据放入缓存
                valueOperations.set(key, result);
            }

            return result;

    }

    @After("@annotation(com.baizhi.annotation.DelCache)")
    public void after(JoinPoint joinPoint){
        System.out.println("===清空缓存===");

        //只要切到处查询方法以外的方法就清空缓存
        String className = joinPoint.getTarget().getClass().getName();

        //获取所有的key
        Set<String> keys = StringRedisTemplate.keys("*");

        //根据Key清空
        for (String key : keys) {
            //判断符合条件的key
            if(key.startsWith(className)){
                //清除
                StringRedisTemplate.delete(key);
            }
        }
    }
}
