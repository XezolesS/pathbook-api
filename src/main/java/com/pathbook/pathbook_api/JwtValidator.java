package com.pathbook.pathbook_api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtValidator {

    @Value("${jwt.secret.key}")
    private String expirationDate ; // Secret Key

    @Value("${jwt.expiration.time}")
    private long expirationTime; // 만료 시간 (밀리초)

    // Secret Key를 Key 객체로 변환
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(expirationDate .getBytes(StandardCharsets.UTF_8));
    }

    // JWT 생성
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 사용자명 설정
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 만료 시간 설정
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 서명 알고리즘 및 키 설정
                .compact();
    }

    // JWT에서 사용자명 추출
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // JWT 검증 후 유효한 토큰인지 체크
    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    // JWT 만료 여부 확인
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // JWT에서 Claims 추출
    private Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey()) // Secret Key 설정
                    .build()
                    .parseClaimsJws(token)
                    .getBody(); // Claims 반환
        } catch (Exception ex) {
            throw new RuntimeException("Invalid JWT token", ex); // 예외 처리
        }
    }

}
