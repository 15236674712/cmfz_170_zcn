package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public interface ChapterService {

    HashMap<String,Object> queryByPage(Integer page, Integer rows,String albumId);

    String add(Chapter chapter);

    void uploadChapter(MultipartFile url, String id, HttpServletRequest request);

    void update(Chapter chapter);

    void delete(Chapter chapter);

    void downloadChapter(String fileName, HttpServletRequest request, HttpServletResponse response);
}
