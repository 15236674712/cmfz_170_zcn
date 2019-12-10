package com.baizhi;

import com.alibaba.fastjson.JSONObject;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGoEasy {

    @Test
    public void TestGoEasy(){

        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-df0a45499f274b2bae29ae50a6a12dc9");
        goEasy.publish("cmfz", "Hello, GoEasy!");
    }

    @Test
    public void TestGoEasyEcharts(){

        for (int i = 0; i < 10; i++) {
            System.out.println(i);

            //获取随机数
            Random random = new Random();
            random.nextInt(10);

            //将随机数放入数组
            int[] randoms ={random.nextInt(500),random.nextInt(100),random.nextInt(900),random.nextInt(400),random.nextInt(300),random.nextInt(800)};

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("month", Arrays.asList("1月","2月","3月","4月","5月","6月"));
            jsonObject.put("boys",randoms);
            jsonObject.put("girls",randoms);

            //将对象转为json格式字符串
            String content = jsonObject.toJSONString();

            //发布消息  发布地址，appkey
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-df0a45499f274b2bae29ae50a6a12dc9");
            //参数: 管道(标识)名称,发布的内容
            goEasy.publish("cmfz", content);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void TestGoEasyEchartes(){

        Random random = new Random();
        random.nextInt();

        Integer[] randoms = {random.nextInt(500),random.nextInt(100),random.nextInt(800),
                             random.nextInt(300),random.nextInt(400),random.nextInt(700),};

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("month", Arrays.asList("1月","2月","3月","4月","5月","6月"));
        jsonObject.put("boys", randoms);
        jsonObject.put("girls", randoms);

        //将json对啊ing转为json字符串
        String content = jsonObject.toJSONString();

        //发布消息  发布地址，appkey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-df0a45499f274b2bae29ae50a6a12dc9");
        //参数: 管道(标识)名称,发布的内容
        goEasy.publish("cmfz", content);
    }

    @Test
    public void TestGoEasyEchartss(){

        for (int i = 0; i < 20; i++) {
            System.out.println(i);

            //获取随机数
            Random random = new Random();
            random.nextInt(10);

            //将随机数放入数组
            int[] randoms ={random.nextInt(500),random.nextInt(100),random.nextInt(900),random.nextInt(400),random.nextInt(300),random.nextInt(800)};

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("month",Arrays.asList("1月","2月","3月","4月","5月","6月"));
            jsonObject.put("boys",randoms);
            jsonObject.put("girls",randoms);

            //将对象转为json格式字符串
            String content = jsonObject.toJSONString();

            //发布消息  发布地址，appkey
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-df0a45499f274b2bae29ae50a6a12dc9");
            //参数: 管道(标识)名称,发布的内容
            goEasy.publish("myChannel162", content);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
