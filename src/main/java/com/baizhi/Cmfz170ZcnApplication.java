package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@tk.mybatis.spring.annotation.MapperScan("com.baizhi.dao")
@org.mybatis.spring.annotation.MapperScan("com.baizhi.dao")
@SpringBootApplication
public class Cmfz170ZcnApplication {

    public static void main(String[] args) {
        SpringApplication.run(Cmfz170ZcnApplication.class, args);
    }

}
