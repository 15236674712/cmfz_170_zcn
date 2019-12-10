package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RequestMapping("chapter")
@RestController
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @RequestMapping("queryByPage")
    public HashMap<String,Object> queryByPage(Integer page,Integer rows,String albumId){

        HashMap<String, Object> map = chapterService.queryByPage(page, rows,albumId);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Chapter chapter, String oper,String albumId){

        System.out.println("++edit chapter++"+chapter);
        String id=null;
        if(oper.equals("add")){
            chapter.setAlbumId(albumId);
            id = chapterService.add(chapter);
        }

        if(oper.equals("edit")){

            chapterService.update(chapter);
        }

        if(oper.equals("del")){

            chapterService.delete(chapter);
        }
        return id;
    }

    @RequestMapping("uploadChapter")
    public void uploadChapter(MultipartFile url, String id, HttpServletRequest request){

        chapterService.uploadChapter(url,id,request);
    }

    @RequestMapping("downloadChapter")
    public void downloadChapter(String fileName, HttpServletRequest request, HttpServletResponse response){
        chapterService.downloadChapter(fileName,request,response);
    }

}
