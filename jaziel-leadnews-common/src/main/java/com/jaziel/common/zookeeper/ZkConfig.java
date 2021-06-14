package com.jaziel.common.zookeeper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author 王杰
 * @date 2021/6/8 20:30
 * 自动化配置核心数据库的连接配置
 */
@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "zk")
@PropertySource("classpath:zookeeper.properties")
public class ZkConfig {

    String host;
    String sequencePath;

    /**
     * 这是最快的连接池
     *
     * @return
     */
    @Bean
    public ZookeeperClient zookeeperClient() {
        return new ZookeeperClient(this.host, this.sequencePath);
    }
}
