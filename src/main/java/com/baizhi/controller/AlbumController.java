package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RequestMapping("album")
@RestController
public class AlbumController {

    @Resource
    private AlbumService albumService;

    @RequestMapping("queryByPage")
    public HashMap<String,Object> queryByPage(Integer page,Integer rows){

        HashMap<String, Object> map = albumService.queryByPage(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Album album, String oper){

        System.out.println("++edit album++"+album);
        String id=null;
        if(oper.equals("add")){
            id = albumService.add(album);
        }

        if(oper.equals("edit")){

            albumService.update(album);
        }

        if(oper.equals("del")){

            albumService.delete(album);
        }
        return id;
    }

    @RequestMapping("uploadAlbum")
    public void uploadAlbum(MultipartFile url, String id, HttpServletRequest request){

        albumService.uploadBanners(url,id,request);
    }

}
