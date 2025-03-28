package com.pathbook.pathbook_api.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.repository.UserRepository;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;

    // TODO: DB 커넥션 체크용, 프로덕션에서는 지워야 함.
    public int testDBConnection() {
        try {
            return userRepository.testTableAccess();
        } catch (Exception ex) {
            return -1;
        }
    }

    public boolean login(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(hashPassword(password));
    }

    private String hashPassword(String password) {
        try { 
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
    
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                
                if(hex.length() == 1) 
                    hexString.append('0');
                hexString.append(hex);
            }
    
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
