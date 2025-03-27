package com.pathbook.pathbook_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    private UserService userService;

    // 로그인 기능
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login login) {
        boolean isAuthenticated = userService.authenticate(login.email(), login.password());
        if (isAuthenticated) {
            return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("로그인 실패: 이메일 또는 비밀번호가 잘못되었습니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    // 회원가입 기능
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Login login) {
        boolean isRegistered = userService.registerUser(login.email(), login.password());
        if (isRegistered) {
            return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("회원가입 실패: 이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
