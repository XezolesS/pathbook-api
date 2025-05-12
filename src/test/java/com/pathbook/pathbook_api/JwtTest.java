package com.pathbook.pathbook_api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.pathbook.pathbook_api.jwt.EmailVerificationJwt;

@SpringBootTest
public class JwtTest {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private EmailVerificationJwt emailVerificationJwt;

    @Test
    void getSecret() {
        assertNotNull(secret);
    }

    @Test
    void testEmailVerificationJwt() {
        String token = emailVerificationJwt.buildToken();

        assertNotNull(token);
    }

}
