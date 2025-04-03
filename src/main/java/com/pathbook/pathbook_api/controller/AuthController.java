package com.pathbook.pathbook_api.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pathbook.pathbook_api.service.AuthService;
import com.pathbook.pathbook_api.entity.User;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String token = authService.login(user.getEmail(), user.getPassword());
        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token)); // JSON 형태로 반환
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String response = authService.register(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //  이메일 인증
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String verificationToken) {
        boolean verified = authService.verifyEmail(verificationToken);
        if (verified) {
            return new ResponseEntity<>("이메일 인증 성공.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("이메일 인증 실패.", HttpStatus.UNAUTHORIZED);
        }
    }
}