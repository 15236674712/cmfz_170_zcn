package com.baizhi.serviceImpl;

import com.baizhi.dao.AdminMapper;
import com.baizhi.entity.Admin;
import com.baizhi.entity.AdminExample;
import com.baizhi.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminDao;

    @Resource
    private AdminExample adminExample;

    @Override
    public HashMap<String, Object> queryByName(String enCode, String username, String password, HttpServletRequest request) {

        String imageCode = (String) request.getSession().getAttribute("imageCode");

        HashMap<String, Object> map = new HashMap<>();

        //判断验证码
        if(imageCode.equals(enCode)){
            //根据用户名查询用户
            adminExample.createCriteria().andUsernameEqualTo(username);
            Admin admin = adminDao.selectOneByExample(adminExample);
            //判断用户
            if(admin!=null){
                //判断密码是否一致
                if(password.equals(admin.getPassword())){
                    //存入session
                    request.getSession().setAttribute("admin",admin);
                    map.put("success","200");
                    map.put("message","登录成功");
                }else{
                    map.put("success","400");
                    map.put("message","密码错误");
                }
            }else{
                map.put("success","400");
                map.put("message","用户不存在");
            }
        }else{
            map.put("success","400");
            map.put("message","验证码错误");
        }
        return map;
    }
}
