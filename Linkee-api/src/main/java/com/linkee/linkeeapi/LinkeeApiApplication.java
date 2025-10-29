package com.linkee.linkeeapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//@MapperScan("com.linkee.linkeeapi.inquiry.mapper")
@EnableJpaAuditing
@SpringBootApplication
public class LinkeeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkeeApiApplication.class, args);
    }

}
