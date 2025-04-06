package com.pathbook.pathbook_api.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pathbook.pathbook_api.service.AuthService;
import com.pathbook.pathbook_api.entity.User;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 로그인 (세션 방식)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user, HttpSession session) {
        boolean success = authService.login(user.getEmail(), user.getPassword(), session);
        if (success) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 이메일 또는 비밀번호입니다.");
        }
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String response = authService.register(user.getUsername(), user.getEmail(), user.getPassword());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 이메일 인증
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String email, @RequestParam String verificationToken) {
        boolean verified = authService.verifyEmail(email, verificationToken);
        if (verified) {
            return new ResponseEntity<>("이메일 인증 성공.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("이메일 인증 실패.", HttpStatus.UNAUTHORIZED);
        }
    }
}
