package com.pathbook.pathbook_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pathbook.pathbook_api.model.LoginRequest;
import com.pathbook.pathbook_api.model.RegisterRequest;
import com.pathbook.pathbook_api.model.UserPrincipal;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
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
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        String encodedPassword = passwordEncoder.encode(registerRequest.password());

        String response = authService.register(
            registerRequest.id(),
            registerRequest.username(),
            registerRequest.email(),
            encodedPassword
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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
