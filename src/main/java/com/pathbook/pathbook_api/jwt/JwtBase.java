package com.pathbook.pathbook_api.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

import javax.crypto.SecretKey;

public abstract class JwtBase {
    @Value("${jwt.secret}")
    protected String secret;

    public JwtBase() {}

    public abstract String buildToken();

    public abstract void verify(String token);

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
