package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.ArticleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RequestMapping("article")
@RestController
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @RequestMapping("queryByPage")
    public HashMap<String,Object> queryByPage(Integer page,Integer rows){

        HashMap<String, Object> map = articleService.queryByPage(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Banner banner,String oper){

        System.out.println("++edit banner++"+banner);
        String id=null;
        if(oper.equals("add")){
            //id = articleService.add(banner);
        }

        if(oper.equals("edit")){

            //articleService.update(banner);
        }

        if(oper.equals("del")){

            //articleService.delete(banner);
        }
        return id;
    }

}
