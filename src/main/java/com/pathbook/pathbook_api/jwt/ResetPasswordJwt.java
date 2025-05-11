package com.pathbook.pathbook_api.jwt;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.PrematureJwtException;

public class ResetPasswordJwt extends JwtBase {

    private static final String SUBJECT = "reset-password";

    private static final long EXPIRATION_SECOND = 60 * 30; // 30 minutes

    private String userId;

    public ResetPasswordJwt() {
        super();
    }

    public ResetPasswordJwt(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String buildToken() {
        Long currentTimeMs = System.currentTimeMillis();

        return Jwts.builder()
                .subject(SUBJECT)
                // .audience("") // Server address?
                .claim("uid", userId)
                .expiration(new Date(currentTimeMs + 1000 * EXPIRATION_SECOND))
                .notBefore(new Date(currentTimeMs))
                .issuedAt(new Date(currentTimeMs))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public void verify(String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .clockSkewSeconds(3 * 60)
                    .requireSubject(SUBJECT)
                    .verifyWith(getSigningKey())
                    .build()
                    .parse(token)
                    .accept(Jws.CLAIMS);

            Claims body = jws.getPayload();
            Date now = new Date();

            if (isExpired(body, now)) {
                throw new ExpiredJwtException(jws.getHeader(), body, "Token has expired");
            }

            if (isNotBefore(body, now)) {
                throw new PrematureJwtException(jws.getHeader(), body, "Token cannot be used yet");
            }

            this.userId = body.get("uid", String.class);
        } catch (JwtException e) {
            throw new JwtException(token, e);
        }
    }

}
