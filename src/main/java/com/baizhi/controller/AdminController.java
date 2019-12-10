package com.baizhi.controller;

import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("getImageCode")
    public void getImageCode(HttpServletRequest request, HttpServletResponse response){

        //1.获取验证码随机字符
        String code = ImageCodeUtil.getSecurityCode();
        System.out.println("验证码："+code);
        //2.存储验证码随机字符
        request.getSession().setAttribute("imageCode",code);
        //3.生成验证码图片
        BufferedImage image = ImageCodeUtil.createImage(code);
        //4.设置相应格式
        response.setContentType("image/png");
        try {
            //5.将验证码图片响应到页面
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("login")
    @ResponseBody
    public HashMap<String, Object> login(String enCode,String username,String password,HttpServletRequest request){

        System.out.println(enCode+"=="+username+"=="+password);
        HashMap<String, Object> map = adminService.queryByName(enCode, username, password, request);
        return map;
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        /*System.out.println("name: "+name);
        Admin admin = new Admin("1", "admin", "111111");*/
        request.getSession().removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }

}
