package com.lswr.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.lswr.demo.model.dao")
@Configuration
public class MyBatisConfig {

}

