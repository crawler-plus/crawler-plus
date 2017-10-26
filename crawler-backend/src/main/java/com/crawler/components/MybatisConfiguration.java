package com.crawler.components;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置
 */
@Configuration
@MapperScan("com.crawler.dao")
public class MybatisConfiguration {
}
