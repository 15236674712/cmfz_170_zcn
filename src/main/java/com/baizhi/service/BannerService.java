package com.baizhi.service;

import com.baizhi.entity.Banner;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface BannerService {

    HashMap<String,Object> queryByPage(Integer page,Integer rows);

    String add(Banner banner);

    void uploadBanners(MultipartFile url, String id, HttpServletRequest request);

    void update(Banner banner);

    void delete(Banner banner);
}
