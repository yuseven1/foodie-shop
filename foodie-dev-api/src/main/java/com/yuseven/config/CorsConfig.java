package com.yuseven.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 设置跨域的配置类
 *
 * @Author Yu Qifeng
 * @Date 2021/2/24 22:41
 * @Version v1.0
 */
@Configuration
public class CorsConfig {

    public CorsConfig() {}

    @Bean
    public CorsFilter corsFilter() {
        // 1. 添加 cors 配置信息
        CorsConfiguration config = new CorsConfiguration();
        // 1.1 添加允许的访问源
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://192.168.196.128:8080");
        config.addAllowedOrigin("http://192.168.196.128:80");
        config.addAllowedOrigin("http://shop.yuseven.com:8080");
        config.addAllowedOrigin("http://shop.yuseven.com");
        config.addAllowedOrigin("http://center.yuseven.com:8080");
        config.addAllowedOrigin("http://center.yuseven.com");
        config.addAllowedOrigin("*");
        // 1.2 设置是否发送cookies信息
        config.setAllowCredentials(true);
        // 1.3 设置允许的请求方式
        config.addAllowedMethod("*");
        // 1.4 设置允许的http header
        config.addAllowedHeader("*");

        // 2. 为 url 添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", config);

        // 3. 返回重新定义好的 corsSource
        return new CorsFilter(corsSource);
    }
}
