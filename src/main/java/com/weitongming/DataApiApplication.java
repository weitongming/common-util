package com.weitongming;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.weitongming.dao.mapper")
@EnableCaching
public class DataApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataApiApplication.class, args);
	}
}
