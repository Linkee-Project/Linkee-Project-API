package com.linkee.linkeeapi.auth.controller;

import com.linkee.linkeeapi.auth.authService.UserAuthService;
import com.linkee.linkeeapi.auth.mail.EmailRequest;
import com.linkee.linkeeapi.auth.mail.EmailService;
import com.linkee.linkeeapi.auth.mail.EmailVerifyRequest;
import com.linkee.linkeeapi.common.security.jwt.JwtTokenProvider;
import com.linkee.linkeeapi.common.security.service.RedisRefreshTokenService;
import com.linkee.linkeeapi.user.command.application.dto.request.UserCreateRequest;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "회원", description = "회원가입, 로그인, 계정, 프로필 관련 API")
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
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        userAuthService.createUser(request);

        return ResponseEntity.ok("회원가입 완료!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {

        String userEmail = request.get("userEmail");
        String password = request.get("password");

        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getUserPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        String role = user.getUserRole().name();
        System.out.println("role: "+role);

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        redisRefreshTokenService.save(userEmail, refreshToken, REFRESH_TOKEN_EXPIRE);

        // 게이트웨이용 추가 정보도 반환
        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken,
                "publicId", user.getPublicId(),
                "role", role
        ));
    }

    @PostMapping("/password/reset/request")
    public ResponseEntity<String> requestPasswordReset(@RequestBody EmailRequest emailRequest) {

        userRepository.findByUserEmail(emailRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다"));

        emailService.sendAuthEmail(emailRequest.getEmail());

        return ResponseEntity.ok("인증 코드가 발송되었습니다.");
    }

    @PostMapping("/password/reset/verify")
    public String verifyAuth(@RequestBody @Valid EmailVerifyRequest req) {
        boolean result = emailService.verifyCode(req.getEmail(), req.getCode());
        return result ? "인증 성공!" : "인증 실패 또는 만료됨";
    }

    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {

        String email = request.get("email");
        String newPassword = request.get("newPassword");

        userAuthService.resetToTemporaryPassword(email, newPassword);

        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("로그아웃 완료!");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {

        String userEmail = request.get("userEmail");
        String refreshToken = request.get("refreshToken");

        User user = userRepository.findByUserEmail(userEmail).orElseThrow();
        if (!redisRefreshTokenService.isValid(userEmail, refreshToken)
                || !jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid refresh token"));
        }



        String newAccessToken = jwtTokenProvider.createAccessToken(user);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

}
