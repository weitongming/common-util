package com.weitongming.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * @author weitongming
 * @Classname TKConfiguration
 * @Description TODO
 * @Date 2020/1/13 15:12
 * @Version V1.0
 */
@Configuration
@AutoConfigureAfter(MybatisConfig.class)
public  class TKConfiguration {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage("com.weitongming.dao.mapper.*");
        //配置通用mappers
        Properties properties = new Properties();
        properties.setProperty("mappers", "com.weitongming.dao.mapper.BaseMapper");
        properties.setProperty("mybatis.mapper-locations","classpath*:**/xml/*.xml");
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
}