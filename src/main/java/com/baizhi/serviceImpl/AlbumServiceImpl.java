package com.baizhi.serviceImpl;

import com.baizhi.annotation.AddCache;
import com.baizhi.dao.AlbumMapper;
import com.baizhi.entity.Album;
import com.baizhi.entity.AlbumExample;
import com.baizhi.entity.BannerExample;
import com.baizhi.service.AlbumService;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Resource
    private AlbumMapper albumMapper;

    @AddCache
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();

        AlbumExample example = new AlbumExample();
        //总条数
        Integer records = albumMapper.selectCountByExample(example);
        map.put("records",records);

        //总页数
        Integer totals=records%rows==0?records/rows:records/rows+1;
        map.put("totals",totals);
        //当前页
        map.put("page",page);

        //分页
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Album> albums = albumMapper.selectByRowBounds(new Album(), rowBounds);
        //数据
        map.put("rows",albums);

        return map;
    }

    @Override
    public String add(Album album) {
        String uuid = UUIDUtil.getUUID();
        album.setId(uuid);
        album.setStatus("1");
        album.setCreateDate(new Date());
        System.out.println("service添加album："+album);

        albumMapper.insert(album);
        //返回添加对象的Id
        return uuid;
    }

    @Override
    public void uploadBanners(MultipartFile url, String id, HttpServletRequest request) {

    }

    @Override
    public void update(Album album) {

    }

    @Override
    public void delete(Album album) {
        System.out.println("==delete--banner: "+album);
        BannerExample example = new BannerExample();
        example.createCriteria().andIdEqualTo(album.getId());
        albumMapper.deleteByExample(example);
    }
}
