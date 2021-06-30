package com.jaziel.media.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 王杰
 * @date 2021/6/24 16:31
 */
@Configuration
@ServletComponentScan("com.jaziel.common.web.wm.security")
public class SecurityConfig {
}
