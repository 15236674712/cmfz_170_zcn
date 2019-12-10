package com.baizhi.service;

import com.baizhi.entity.Album;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface AlbumService {

    HashMap<String,Object> queryByPage(Integer page, Integer rows);

    String add(Album album);

    void uploadBanners(MultipartFile url, String id, HttpServletRequest request);

    void update(Album album);

    void delete(Album album);
}
