package com.linkee.linkeeapi.common.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKeyBase64,
            @Value("${jwt.access-expiration}") long accessTokenValidity,
            @Value("${jwt.refresh-expiration}") long refreshTokenValidity) {

        // Base64 디코딩 후 SecretKey 생성
        byte[] decodedKey = Base64.getDecoder().decode(secretKeyBase64);
        this.key = Keys.hmacShaKeyFor(decodedKey);

        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
    }


    // AccessToken 생성
    public String createAccessToken(String username) {
        return createToken(username, accessTokenValidity);
    }

    // RefreshToken 생성
    public String createRefreshToken(String username) {
        return createToken(username, refreshTokenValidity);
    }

    // 토큰 생성
    private String createToken(String username, long expireTime) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireTime))
                .signWith(key) // HS256 자동 적용
                .compact();
    }

    // 토큰에서 username 추출
    public String getUsername(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }



}