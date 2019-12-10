package com.baizhi.service;

import java.util.HashMap;

public interface ArticleService {

    HashMap<String,Object> queryByPage(Integer page, Integer rows);

}
