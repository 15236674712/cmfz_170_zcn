package com.baizhi.serviceImpl;

import com.baizhi.annotation.AddCache;
import com.baizhi.dao.ArticleMapper;
import com.baizhi.entity.Article;
import com.baizhi.entity.ArticleExample;
import com.baizhi.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @AddCache
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();

        ArticleExample example = new ArticleExample();
        //总条数
        Integer records = articleMapper.selectCountByExample(example);
        map.put("records",records);

        //总页数
        Integer totals=records%rows==0?records/rows:records/rows+1;
        map.put("totals",totals);
        //当前页
        map.put("page",page);

        //分页
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Article> articles = articleMapper.selectByRowBounds(new Article(), rowBounds);
        //数据
        map.put("rows",articles);
        return map;
    }
}
