package com.jaziel.common.common.redis;

import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author Jaziel
 * @date 2021/8/9 14:45
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@PropertySource("classpath:redis.properties")
public class RedisConfiguration extends RedisAutoConfiguration {
}
