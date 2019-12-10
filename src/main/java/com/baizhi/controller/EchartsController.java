package com.baizhi.controller;

import com.baizhi.entity.City;
import com.baizhi.entity.Pro;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Controller
@RequestMapping("echarts")
public class EchartsController {

    @RequestMapping("queryUser")
    @ResponseBody
    public HashMap<String, Object> queryUser(){

        System.out.println("======");
        HashMap<String, Object> map = new HashMap<>();
        map.put("month", Arrays.asList("1月","2月","3月","4月","5月","6月"));
        map.put("boys", Arrays.asList(5, 200, 36, 100, 10, 20));
        map.put("girls", Arrays.asList(5, 200, 400, 100, 100, 200));

        return map;
    }

    @RequestMapping("queryUserChina")
    @ResponseBody
    public ArrayList<Pro> queryUserChina(){

        City city1 = new City("北京","300");
        City city2 = new City("河南","700");
        City city3 = new City("山东","500");
        City city4 = new City("云南","900");
        //封装 city 集合
        ArrayList<City> boys = new ArrayList<>();
        boys.add(city1);
        boys.add(city2);
        boys.add(city3);
        boys.add(city4);

        //封装 city 集合
        ArrayList<City> girls = new ArrayList<>();
        girls.add(new City("天津","400"));
        girls.add(new City("上海","700"));
        girls.add(new City("广东","200"));
        girls.add(new City("海南","800"));

        Pro pro1 = new Pro("小姐姐",girls);
        Pro pro2 = new Pro("小哥哥",boys);

        //封装  最外层集合
        ArrayList<Pro> pros = new ArrayList<>();
        pros.add(pro1);
        pros.add(pro2);

        return pros;
    }
}
