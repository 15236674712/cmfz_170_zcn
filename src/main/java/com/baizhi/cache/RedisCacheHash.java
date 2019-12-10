package com.baizhi.cache;


import com.baizhi.annotation.AddCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.lang.reflect.Method;

//@Configuration
//@Aspect
public class RedisCacheHash {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate StringRedisTemplate;

    //@Around("execution(* com.baizhi.serviceImpl.*.query*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        System.out.println("====环绕通知====");

        /*解决缓存乱码*/
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        //获取切面切到的方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        //判断该方法上是否有加入缓存的注解
        boolean annotationPresent = method.isAnnotationPresent(AddCache.class);
        if(annotationPresent){
            //创建一个可边长字符串
            StringBuilder sb = new StringBuilder();

            //设置key  value
            //类的全限定名+方法名+实参
            //获取类的全限定名
            String clazzName = proceedingJoinPoint.getTarget().getClass().getName();


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

            //Hash类型的操作
            HashOperations hashOperations = redisTemplate.opsForHash();

            //判断缓存中是否有数据  根据key判断
            Boolean aBooleanKey = hashOperations.hasKey(clazzName,key);

            //结果
            Object result = null;

            //判断key是否存在
            if(aBooleanKey){
                //key存在
                //有数据  查询数据  放行
                result = hashOperations.get(clazzName,key);
            }else{
                //key不存在
                //获取数据
                result = proceedingJoinPoint.proceed();

                //没有数据  将数据放入缓存
                hashOperations.put(clazzName,key,result);
            }
            return result;
        }else{
            //没有该注解直接放行
            Object result = proceedingJoinPoint.proceed();
            return result;
        }
    }

    //@After("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*.query*(..)) ")
    public void after(JoinPoint joinPoint){
        System.out.println("===清空缓存===");

        //只要切到处查询方法以外的方法就清空缓存
        String className = joinPoint.getTarget().getClass().getName();

        StringRedisTemplate.delete(className);

    }

}
