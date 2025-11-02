package com.linkee.linkeeapi.common.controller;


import com.linkee.linkeeapi.common.jwt.JwtTokenProvider;
import com.linkee.linkeeapi.common.service.RedisRefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRefreshTokenService redisRefreshTokenService;

    private final long REFRESH_TOKEN_EXPIRE = 1000 * 60 * 60; // 1시간

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username) {
        String accessToken = jwtTokenProvider.createAccessToken(username);
        String refreshToken = jwtTokenProvider.createRefreshToken(username);

        redisRefreshTokenService.save(username, refreshToken, REFRESH_TOKEN_EXPIRE);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestParam String username,
                                                       @RequestParam String refreshToken) {
        if (!redisRefreshTokenService.isValid(username, refreshToken)
                || !jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid refresh token"));
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(username);
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    @GetMapping("/secure")
    public ResponseEntity<String> secure() {
        return ResponseEntity.ok("✅ Secure access success!");
    }
}
