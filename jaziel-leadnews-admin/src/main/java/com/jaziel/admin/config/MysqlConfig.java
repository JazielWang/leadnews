package com.jaziel.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 王杰
 * @date 2021/5/18 19:21
 */
@Configuration
@ComponentScan("com.jaziel.common.mysql.core")
@MapperScan("com.jaziel.admin.dao")
public class MysqlConfig {
}
