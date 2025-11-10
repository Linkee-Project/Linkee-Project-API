package com.example.linkeeeureksserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer // 유레카 서버 어플리케이션
public class LinkeeEureksServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkeeEureksServerApplication.class, args);
    }

}
