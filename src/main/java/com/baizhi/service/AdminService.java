package com.baizhi.service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface AdminService {

    HashMap<String,Object> queryByName(String enCode, String username, String password, HttpServletRequest request);

}
