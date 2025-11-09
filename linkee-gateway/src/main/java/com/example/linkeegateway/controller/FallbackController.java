package com.example.linkeegateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {


    private final Environment environment;

    public FallbackController(Environment environment) {
        this.environment = environment;
    }

    @RequestMapping("/fallback/user")
    public Mono<String> userFallback() {
        return Mono.just("현재 사용자 서비스에 장애가 있습니다. 잠시 후 다시 시도해주세요.");
    }

    @GetMapping("/health_check")
    public String status(){
        return String.format("It's working in user service on PORT %s", environment.getProperty("local.server.port"));
    }
}
