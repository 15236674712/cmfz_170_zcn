package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RequestMapping("banner")
@RestController
public class BannerController {

    @Resource
    private BannerService bannerService;

    @RequestMapping("queryByPage")
    public HashMap<String,Object> queryByPage(Integer page,Integer rows){

        System.out.println("===="+page+"--"+rows);

        HashMap<String, Object> map = bannerService.queryByPage(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Banner banner,String oper){

        System.out.println("++edit banner++"+banner);
        String id=null;
        if(oper.equals("add")){
            id = bannerService.add(banner);
        }

        if(oper.equals("edit")){

            bannerService.update(banner);
        }

        if(oper.equals("del")){

            bannerService.delete(banner);
        }
        return id;
    }

    @RequestMapping("uploadBanners")
    public void uploadBanners(MultipartFile url, String id, HttpServletRequest request){

        bannerService.uploadBanners(url,id,request);
    }

}
