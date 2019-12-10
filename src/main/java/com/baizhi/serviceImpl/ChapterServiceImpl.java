package com.baizhi.serviceImpl;

import com.baizhi.dao.ChapterMapper;
import com.baizhi.entity.BannerExample;
import com.baizhi.entity.Chapter;
import com.baizhi.entity.ChapterExample;
import com.baizhi.service.ChapterService;
import com.baizhi.util.UUIDUtil;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.RowBounds;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows,String albumId) {
        HashMap<String, Object> map = new HashMap<>();

        ChapterExample example = new ChapterExample();
        //总条数
        Integer records = chapterMapper.selectCountByExample(example);
        map.put("records",records);

        //总页数
        Integer totals=records%rows==0?records/rows:records/rows+1;
        map.put("totals",totals);
        //当前页
        map.put("page",page);

        //分页
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        example.createCriteria().andAlbumIdEqualTo(albumId);
        List<Chapter> chapters = chapterMapper.selectByExampleAndRowBounds(example,rowBounds);
        //数据
        map.put("rows",chapters);
        return map;
    }

    @Override
    public String add(Chapter chapter) {
        System.out.println("====="+chapter);
        String uuid = UUIDUtil.getUUID();
        chapter.setId(uuid);
        chapter.setCreateDate(new Date());
        System.out.println("service添加chapter："+chapter);
        chapterMapper.insertSelective(chapter);

        return uuid;
    }

    @Override
    public void uploadChapter(MultipartFile url, String id, HttpServletRequest request) {
        String realPath = request.getSession().getServletContext().getRealPath("/upload/audio");

        //System.out.println("==realPath="+realPath);
        //System.out.println("==url="+url);
        //System.out.println("==id="+id);

        File file = new File(realPath);

        if (!file.exists()) {
            file.mkdirs();
        }
        try {

            System.out.println("==+++="+url.getOriginalFilename());
            String Filename = url.getOriginalFilename();


            System.out.println("==Filename="+Filename);
            String name = new Date().getTime() + "_" + Filename;
            //System.out.println("==name="+name);
            url.transferTo(new File(realPath, name));

            //获取文件大小   字节
            long size = url.getSize();
            String str = String.valueOf(size);
            Double aDouble = Double.valueOf(str);
            double sss=aDouble/1024/1024;

            DecimalFormat format = new DecimalFormat("0.00");
            String sizes =format.format(sss)+"MB";
            System.out.println(sizes);

            //获取文件时长
            AudioFile audioFile = AudioFileIO.read(new File(realPath, name));

            //获取时长 秒
            int length = audioFile.getAudioHeader().getTrackLength();

            String duration = length / 60 + "分" + length % 60 + "秒";
            System.out.println(length);
            System.out.println(duration);

            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setUrl(name);
            chapter.setSize(sizes);
            chapter.setDuration(duration);

            BannerExample example = new BannerExample();
            example.createCriteria().andIdEqualTo(id);

            chapterMapper.updateByExampleSelective(chapter,example);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Chapter chapter) {

    }

    @Override
    public void delete(Chapter chapter) {

    }

    @Override
    public void downloadChapter(String fileName, HttpServletRequest request, HttpServletResponse response) {

        String realPath = request.getSession().getServletContext().getRealPath("/upload/audio");

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(realPath,fileName));
            response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(fileName,"UTF-8"));
            ServletOutputStream outputStream = response.getOutputStream();

            //下载文件
            IOUtils.copy(fileInputStream,outputStream);

            //关闭资源
            fileInputStream.close();
            outputStream.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
