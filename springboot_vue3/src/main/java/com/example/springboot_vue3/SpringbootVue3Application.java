package com.example.springboot_vue3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springboot_vue3.mapper") //扫描，之后不用在mapper类中加@mapper注解
public class SpringbootVue3Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootVue3Application.class, args);
    }

}
