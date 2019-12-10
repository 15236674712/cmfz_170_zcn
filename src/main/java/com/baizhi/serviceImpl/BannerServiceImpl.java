package com.baizhi.serviceImpl;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.DelCache;
import com.baizhi.dao.BannerMapper;
import com.baizhi.entity.Banner;
import com.baizhi.entity.BannerExample;
import com.baizhi.service.BannerService;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    @Resource
    private BannerMapper bannerMapper;

    @AddCache
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {

        HashMap<String, Object> map = new HashMap<>();

        BannerExample example = new BannerExample();
        //总条数
        Integer records = bannerMapper.selectCountByExample(example);
        map.put("records",records);

        //总页数
        Integer totals=records%rows==0?records/rows:records/rows+1;
        map.put("totals",totals);
        //当前页
        map.put("page",page);

        //分页
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Banner> banners = bannerMapper.selectByRowBounds(new Banner(), rowBounds);
        //数据
        map.put("rows",banners);
        return map;
    }

    @DelCache
    @Override
    public void update(Banner banner) {

        if(banner.getUrl()==""){
            banner.setUrl(null);
        }
        BannerExample example = new BannerExample();
        example.createCriteria().andIdEqualTo(banner.getId());
        System.out.println("==update--banner: "+banner);
        bannerMapper.updateByExampleSelective(banner, example);
    }

    @DelCache
    @Override
    public void delete(Banner banner) {
        System.out.println("==delete--banner: "+banner);
        BannerExample example = new BannerExample();
        example.createCriteria().andIdEqualTo(banner.getId());
        bannerMapper.deleteByExample(example);
    }

    @DelCache
    @Override
    public String add(Banner banner) {
        System.out.println("====="+banner);
        String uuid = UUIDUtil.getUUID();
        banner.setId(uuid);
        banner.setStatus("1");
        banner.setCreateDate(new Date());
        System.out.println("service添加轮播图："+banner);

        bannerMapper.insert(banner);
        //返回添加对象的Id
        return uuid;
    }

    @Override
    public void uploadBanners(MultipartFile url, String id, HttpServletRequest request) {
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");

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

            Banner banner = new Banner();
            banner.setId(id);
            banner.setUrl(name);

            BannerExample example = new BannerExample();
            example.createCriteria().andIdEqualTo(id);

            bannerMapper.updateByExampleSelective(banner,example);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
