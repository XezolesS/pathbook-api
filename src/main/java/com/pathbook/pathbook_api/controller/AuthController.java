package com.pathbook.pathbook_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.model.LoginInfo;
import com.pathbook.pathbook_api.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // TODO: DB 커넥션 체크용, 프로덕션에서는 지워야 함.
    @GetMapping("/test")
    public String testDBConnection() {
        return String.valueOf(authService.testDBConnection());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginInfo loginInfo) {
        boolean isAuthenticated = authService.login(loginInfo.email(), loginInfo.password());
        if (isAuthenticated) {
            return new ResponseEntity<>("Login Succeed.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Login Failed.", HttpStatus.UNAUTHORIZED);
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