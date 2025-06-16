package com.pathbook.pathbook_api.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

/**
 * JWT 클래스를 정의하기 위한 추상 클래스입니다.
 *
 * <p>{@link #getClaim(String)}와 {@link #setClaim(String, Object)} 메서드를 사용하여 claim을 추가하여 사용합니다.
 *
 * <p>구현시 {@link org.springframework.stereotype.Component Component}를 어노테이트 하고, {@link
 * org.springframework.beans.factory.annotation.Autowired Autowired}로 dependency inject 하여 사용할 수
 * 있습니다.
 */
public abstract class JwtBase {
    @Value("${jwt.secret}")
    protected String secret;

    protected Header header;
    protected Map<String, Object> claims = new HashMap<>();

    private String subject;
    private long expiration_second;

    public JwtBase() {
        this("jwt-token");
    }

    public JwtBase(String subject) {
        this(subject, 60 * 60 * 24);
    }

    public JwtBase(String subject, long expiration_second) {
        this.subject = subject;
        this.expiration_second = expiration_second;
    }

    /**
     * Header를 반환합니다.
     *
     * @return header
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Claim 리스트를 반환합니다.
     *
     * @return claims
     */
    public Claims getClaims() {
        return (Claims) claims;
    }

    /**
     * Claim의 값을 설정합니다.
     *
     * @param name claim 이름
     * @param value claim 값
     */
    protected JwtBase setClaim(String name, Object value) {
        claims.put(name, value);

        return this;
    }

    /**
     * Claim의 값을 반환합니다.
     *
     * @param name claim 이름
     * @return claim 값
     */
    protected Object getClaim(String name) {
        if (!claims.containsKey(name)) {
            throw new MissingClaimException(
                    header,
                    (Claims) claims,
                    name,
                    null,
                    String.format("Claim not found: %s", name));
        }

        return claims.get(name);
    }

    /**
     * 토큰을 생성합니다.
     *
     * @return JWT 토큰 문자열
     */
    public String buildToken() {
        Long currentTimeMs = System.currentTimeMillis();

        return Jwts.builder()
                .subject(subject)
                // .audience("") // Server address?
                .claims(claims)
                .expiration(new Date(currentTimeMs + 1000 * expiration_second))
                .notBefore(new Date(currentTimeMs))
                .issuedAt(new Date(currentTimeMs))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 토큰을 검증하고 claim을 로드합니다.
     *
     * @param token JWT 토큰 문자열
     * @throws {@link ExpiredJwtException} JWT 토큰 만료시 예외 발생
     * @throws {@link PrematureJwtException} JWT 토큰 사용 가능한 시간 전인 경우 예외 발생
     * @throws {@link JwtException} 기타 JWT 관련 예외
     */
    public void verify(String token) {
        try {
            Jws<Claims> jws =
                    Jwts.parser()
                            .clockSkewSeconds(3 * 60) // 시간 보정: 3분
                            .requireSubject(subject)
                            .verifyWith(getSigningKey())
                            .build()
                            .parse(token)
                            .accept(Jws.CLAIMS);

            JwsHeader header = jws.getHeader();
            Claims body = jws.getPayload();
            Date now = new Date();

            if (isExpired(body, now)) {
                throw new ExpiredJwtException(header, body, "Token has expired");
            }

            if (isNotBefore(body, now)) {
                throw new PrematureJwtException(header, body, "Token cannot be used yet");
            }

            this.header = header;
            claims = body;
        } catch (JwtException e) {
            throw new JwtException(token, e);
        }
    }

    protected SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    protected boolean isExpired(Claims body, Date now) {
        Date expiration = body.getExpiration();
        return expiration != null && expiration.before(now);
    }

    protected boolean isNotBefore(Claims body, Date now) {
        Date notBefore = body.getNotBefore();
        return notBefore != null && notBefore.after(now);
    }
}
