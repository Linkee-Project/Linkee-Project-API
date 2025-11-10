package com.linkee.linkeechatservice.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                    // 모든 경로 허용
                .allowedOrigins("http://localhost:8000") // 허용할 프론트 포트
                .allowedMethods("*")                   // GET, POST, PUT, DELETE 등 모두 허용
                .allowCredentials(true);              // 쿠키/인증 정보 허용
    }
}