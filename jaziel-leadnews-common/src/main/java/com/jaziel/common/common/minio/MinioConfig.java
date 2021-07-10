package com.jaziel.common.common.minio;

import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "minio")
@PropertySource("classpath:minio.properties")
public class MinioConfig{
    private String endpoint;
    private int port;
    private String accessKey;
    private String secretKey;
    private Boolean secure;
    private String bucketName;

    @Bean
    public MinioClient MinioClient() throws Exception {
        MinioClient minioClient = MinioClient.builder().endpoint(endpoint, port, secure)
                .credentials(accessKey, secretKey)
                .build();
        return minioClient;
    }
}
