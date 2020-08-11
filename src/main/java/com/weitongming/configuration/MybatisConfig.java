package com.weitongming.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Mybatis配置
 * @author Louis
 * @date Oct 29, 2018
 */
@Configuration
public class MybatisConfig {

  @Autowired
  private DataSource dataSource;

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {

    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setTypeAliasesPackage("com.sgmw.kitty.*.model");	// 扫描Model

	PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	// 扫描映射文件
	sessionFactory.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));


    return sessionFactory.getObject();
  }
}