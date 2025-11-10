package com.linkee.linkeeapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.linkee.linkeeapi")
@EnableDiscoveryClient
public class LinkeeApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinkeeApiApplication.class, args);
    }
}

