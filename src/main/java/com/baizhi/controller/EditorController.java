package com.baizhi.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("editor")
public class EditorController {


    /*
    *
    * //成功时
    {
            "error" : 0,
            "url" : "http://www.example.com/path/to/file.ext"
    }
    //失败时
    {
            "error" : 1,
            "message" : "错误信息"
    }
    * */
    @RequestMapping("upload")
    public HashMap<String,Object> upload(MultipartFile image, HttpServletRequest request, HttpServletResponse response){


        HashMap<String, Object> map = new HashMap<>();

        try {

            String realPath = request.getSession().getServletContext().getRealPath("/upload/editor");

            File file = new File(realPath);

            if (!file.exists()) {
                file.mkdirs();
            }

            String Filename = image.getOriginalFilename();
            String name = new Date().getTime() + "_" + Filename;
            image.transferTo(new File(realPath, name));

            String scheme = request.getScheme();

            String serverName = request.getServerName();

            int serverPort = request.getServerPort();

            String contextPath = request.getContextPath();

            //"http://localhost:8989/cmfz/upload/editor/"+name
            String url=scheme+"://"+serverName+":"+serverPort+contextPath+"/upload/editor/"+name;

            map.put("error",0);
            map.put("url",url);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("error",1);
            map.put("message","上传失败");
        }
        return map;
    }


    /*
    * {
      "moveup_dir_path": "",
      "current_dir_path": "",
      "current_url": "/ke4/php/../attached/",
      "total_count": 5,
      "file_list": [
        {
          "is_dir": false,
          "has_file": false,
          "filesize": 208736,
          "dir_path": "",
          "is_photo": true,
          "filetype": "jpg",
          "filename": "1241601537255682809.jpg",
          "datetime": "2018-06-06 00:36:39"
        }
      ]
    }
    * */
    @RequestMapping("showPhotos")
    public HashMap<String,Object> showPhotos(HttpServletRequest request){


        HashMap<String, Object> maps = new HashMap<>();

        ArrayList<Object> lists = new ArrayList<>();

        String realPath = request.getSession().getServletContext().getRealPath("/upload/editor");

        //获取文件夹
        File file = new File(realPath);

        //获取问价夹下所有的文件
        String[] names = file.list();

        for (int i = 0; i < names.length; i++) {
            //获取文件名
            String name = names[i];

            HashMap<String, Object> map = new HashMap<>();

            map.put("is_dir",false);  //是否是文件夹
            map.put("has_file",false);  //是否是文件
            File file1 = new File(realPath, name);
            map.put("filesize",file1.length());  //文件的大小
            map.put("is_photo",true);  //是否是图片

            String extension = FilenameUtils.getExtension(name);

            map.put("filetype",extension);  //图片类型
            map.put("filename",name);  //文件名

            String[] split = name.split("_");
            String times =split[0];
            long time = Long.parseLong(times);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
            String datatime = dateFormat.format(time);
            map.put("datetime",datatime);  //上传时间

            lists.add(map);
        }

        maps.put("current_url","http://localhost:8989/cmfz/upload/editor/");
        maps.put("total_count",lists.size());
        maps.put("file_list",lists);

        return maps;
    }

}
