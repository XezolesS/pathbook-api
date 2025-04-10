package com.pathbook.pathbook_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.pathbook.pathbook_api.dto.LoginRequest;
import com.pathbook.pathbook_api.dto.RegisterRequest;
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
                encodedPassword
            );
    
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
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        return ResponseEntity.ok("User authenticated successfully.");
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

}
