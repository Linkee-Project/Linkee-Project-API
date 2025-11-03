package com.linkee.linkeeapi.auth.controller;

import com.linkee.linkeeapi.auth.authService.UserAuthService;
import com.linkee.linkeeapi.auth.mail.EmailService;
import com.linkee.linkeeapi.common.jwt.JwtTokenProvider;
import com.linkee.linkeeapi.common.security.service.RedisRefreshTokenService;
import com.linkee.linkeeapi.user.command.application.dto.request.UserCreateRequest;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRefreshTokenService redisRefreshTokenService;
    private final UserAuthService userAuthService;
    private final EmailService emailService;

    private final long REFRESH_TOKEN_EXPIRE = 1000 * 60 * 60; // 1시간

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody UserCreateRequest request) {

        if (userRepository.findByUserEmail(request.getUserEmail()).isPresent()) {
            // 이미 존재하는 이메일일 때 처리
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        if (!emailService.verifyCode(request.getUserEmail(), request.getInputCode())) {
            throw new IllegalArgumentException("이메일 인증이 필요합니다.");
        }

        userAuthService.createUser(request);

        return ResponseEntity.ok("회원가입 완료!");
    }




    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String userEmail,
                                   @RequestParam String password) {

        User user = userRepository.findByUserEmail(userEmail).orElse(null);

        if (user == null || !passwordEncoder.matches(password, user.getUserPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        String role = user.getUserRole().name();
        String accessToken = jwtTokenProvider.createAccessToken(userEmail, role);
        String refreshToken = jwtTokenProvider.createRefreshToken(userEmail);

        redisRefreshTokenService.save(userEmail, refreshToken, REFRESH_TOKEN_EXPIRE);



        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String userEmail,
                                     @RequestParam String refreshToken) {

        if (!redisRefreshTokenService.isValid(userEmail, refreshToken)
                || !jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid refresh token"));
        }

        String role = jwtTokenProvider.getRole(refreshToken);
        String newAccessToken = jwtTokenProvider.createAccessToken(userEmail, role);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }



}
