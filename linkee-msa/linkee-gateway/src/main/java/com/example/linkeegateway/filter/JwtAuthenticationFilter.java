package com.example.linkeegateway.filter;

import com.example.linkeegateway.jwt.GatewayJwtTokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final GatewayJwtTokenProvider gatewayJwtTokenProvider;

    public JwtAuthenticationFilter(GatewayJwtTokenProvider gatewayJwtTokenProvider) {
        this.gatewayJwtTokenProvider = gatewayJwtTokenProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("요청주소: " + exchange.getRequest().getURI());

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        // 토큰 없으면 그냥 통과
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);

        // JWT 검증
        try {
            if (!gatewayJwtTokenProvider.validateToken(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // JWT에서 정보 꺼내기

        Claims claims = gatewayJwtTokenProvider.getClaims(token);
        if (claims != null) {

        }
            String id = String.valueOf(claims.get("id"));
            String email = claims.getSubject();
            String nickname = claims.get("nickname", String.class);
            String role = claims.get("role", String.class);

        // 헤더 추가
        ServerHttpRequest mutated = exchange.getRequest().mutate()
                .header("X-User-UserId", id)
                .header("X-User-Email", email)
                .header("X-User-NickName", nickname)
                .header("X-User-Role", "ROLE_" + role)
                .build();

        System.out.println("Gateway -> Core-Service Headers: " + mutated.getHeaders());

        return chain.filter(exchange.mutate().request(mutated).build());
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
