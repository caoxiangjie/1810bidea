package com.jk.xys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@MapperScan("com.jk.xys.dao")
@EnableTransactionManagement
public class XysApplication {

    public static void main(String[] args) {
        SpringApplication.run(XysApplication.class, args);
    }

}
