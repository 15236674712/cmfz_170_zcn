package com.baizhi;

import com.baizhi.dao.AdminMapper;
import com.baizhi.entity.Admin;
import com.baizhi.entity.AdminExample;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Cmfz170ZcnApplicationTests {

    @Resource
   private AdminMapper adminm;

    @Resource
    AdminExample example;

    @Test
    public void insert() {

        Admin admin = adminm.queryByUsername("zhangcn");

        System.out.println(admin);
    }

    @Test
    public void insesrt() {

        //adminDao.insert(new Admin("111","aaa","111111"));
        //int bbb = adminDao.insertSelective(new Admin("110", "bbb", "111111"));
        //System.out.println(bbb);
        //AdminExample example = new AdminExample();
        RowBounds rowBounds = new RowBounds(0,3);

        List<Admin> admins = adminm.selectByExampleAndRowBounds(example, rowBounds);
        for (Admin admin : admins) {
            System.out.println(admin);
        }

    }


   /* @Test
    public void insert() {

        //adminDao.insert(new Admin("111","aaa","111111"));
        //int bbb = adminDao.insertSelective(new Admin("110", "bbb", "111111"));
        //System.out.println(bbb);
        //AdminExample example = new AdminExample();
        example.createCriteria().andIdEqualTo("5");

        int i = adminDao.deleteByExample(example);
    }


    @Test
    public void delete() {

        example.createCriteria().andIdEqualTo("5");

        int i = adminDao.deleteByExample(example);
    }

    @Test
    public void update() {

        //adminDao.insert(new Admin("111","aaa","111111"));
        //int bbb = adminDao.insertSelective(new Admin("110", "bbb", "111111"));
        //System.out.println(bbb);
        //AdminExample example = new AdminExample();
        example.createCriteria().andIdEqualTo("222");

        Admin admin = new Admin(null,"ssss","111111");

        adminDao.updateByExample(admin,example);
        //adminDao.updateByExampleSelective(admin,example);
    }

    @Test
    public void select() {

        //adminDao.insert(new Admin("111","aaa","111111"));
        //int bbb = adminDao.insertSelective(new Admin("110", "bbb", "111111"));
        //System.out.println(bbb);
        //AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();

        example.or().andIdEqualTo("1");
        example.or().andPasswordEqualTo("zhangcn");

        List<Admin> admins = adminDao.selectByExample(example);
        for (Admin admin : admins) {
            System.out.println(admins);
        }
    }*/

}
