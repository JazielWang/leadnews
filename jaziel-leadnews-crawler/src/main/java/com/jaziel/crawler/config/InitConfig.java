package com.jaziel.crawler.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan({"com.jaziel.common.common.init", "com.jaziel.common.mysql.core", "com.jaziel.common.kafka"})
@EnableScheduling
public class InitConfig {
}
