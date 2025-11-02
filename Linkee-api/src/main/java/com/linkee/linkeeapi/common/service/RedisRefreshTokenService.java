package com.linkee.linkeeapi.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisRefreshTokenService {

    private final StringRedisTemplate redisTemplate;
    private static final String PREFIX = "refresh:";

    public void save(String username, String refreshToken, long expirationMillis) {
        redisTemplate.opsForValue().set(
                PREFIX + username,
                refreshToken,
                Duration.ofMillis(expirationMillis)
        );
    }

    public boolean isValid(String username, String refreshToken) {
        String savedToken = redisTemplate.opsForValue().get(PREFIX + username);
        return savedToken != null && savedToken.equals(refreshToken);
    }

    public void delete(String username) {
        redisTemplate.delete(PREFIX + username);
    }
}
