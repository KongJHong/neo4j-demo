package com.hong.neo4j.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @Author : KongJHong
 * @Date : 2020-12-25 14:04
 * @Version : 1.0
 * Description     :
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.hong.neo4j.mapper")
public class MybatisPlusConfig {

	private final DataSource dataSource;


	public MybatisPlusConfig (DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	public EasySqlInjector easySqlInjector() {
		return new EasySqlInjector();
	}

	@Bean
	public GlobalConfig globalConfiguration() {
		GlobalConfig conf = new GlobalConfig();
		conf.setSqlInjector(easySqlInjector());
		return conf;
	}
}
