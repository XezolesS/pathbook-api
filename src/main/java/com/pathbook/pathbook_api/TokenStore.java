package com.pathbook.pathbook_api;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStore {

    private final Map<String, String> tokenMap = new ConcurrentHashMap<>();

    public void storeToken(String email, String verificationToken) {
        tokenMap.put(email,verificationToken);
    }

    public String getVerificationToken(String email) {
        return tokenMap.get(email);
    }

    public void removeVerificationToken(String email) {
        tokenMap.remove(email);
    }
}
