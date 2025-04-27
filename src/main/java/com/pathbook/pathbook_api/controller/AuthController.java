package com.pathbook.pathbook_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pathbook.pathbook_api.dto.LoginRequest;
import com.pathbook.pathbook_api.dto.RegisterRequest;
import com.pathbook.pathbook_api.dto.ResetPasswordRequest;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            // TODO: 계정 잠금 로직 변경, 실패 시 반환 코드 변경 (403)
            boolean loginSuccess = authService.handleLogin(loginRequest.email(), loginRequest.password());
            if (loginSuccess == false) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password, account is locked");
            }

            Authentication authenticationRequest = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.email(),
                    loginRequest.password()
                )
            );
    
            // 세션 생성
            SecurityContextHolder.getContext().setAuthentication(authenticationRequest);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            
            UserPrincipal userPrincipal = (UserPrincipal) authenticationRequest.getPrincipal();
    
            return new ResponseEntity<>(userPrincipal, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Authentication failed: " + e.getMessage());
        } 
    }

    @PostMapping("/register")
    public ResponseEntity<?> postRegister(@RequestBody RegisterRequest registerRequest) {
        try {
            String encodedPassword = passwordEncoder.encode(registerRequest.password());
            boolean userAdded = authService.addUser(
                registerRequest.id(),
                registerRequest.username(),
                registerRequest.email(),
                encodedPassword);

            if (userAdded) {
                return new ResponseEntity<>("Successfully registered.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to register.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> postLogout(HttpServletRequest request) {
        return ResponseEntity.ok("Logout");
    }

    // 유저 로그인 디버그용
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userPrincipal);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> getVerify(@RequestParam String id, @RequestParam String token) {
        boolean verified = authService.verifyUser(id, token);
        if (verified) {
            return new ResponseEntity<>("Successfully verified.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to verify.", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        // TODO: GET 리퀘스트에선 페이지 표시
        boolean result = authService.sendResetPasswordEmail(userPrincipal.getEmail());
        if (result) {
            return new ResponseEntity<>("Successfully sent", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to send email.", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
        @RequestBody ResetPasswordRequest resetPasswordRequest,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        // TODO: GET 리퀘스트에선 페이지 표시
        boolean result = authService.resetPassword(
            userPrincipal.getId(),
            resetPasswordRequest.token(),
            resetPasswordRequest.newPassword()
        );

        if (result) {
            return new ResponseEntity<>("Successfully reset password.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to send reset password.", HttpStatus.BAD_REQUEST);
        }
    }

}
