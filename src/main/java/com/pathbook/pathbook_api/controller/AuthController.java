package com.pathbook.pathbook_api.controller;

import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.exception.UserNotFoundException;
import com.pathbook.pathbook_api.request.ChangeUsernameRequest;
import com.pathbook.pathbook_api.request.ForgotPasswordRequest;
import com.pathbook.pathbook_api.request.LoginRequest;
import com.pathbook.pathbook_api.request.RegisterRequest;
import com.pathbook.pathbook_api.request.ResetPasswordRequest;
import com.pathbook.pathbook_api.response.UserResponse;
import com.pathbook.pathbook_api.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> postLogin(
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            // TODO: 계정 잠금 로직 변경, 실패 시 반환 코드 변경 (403)
            boolean loginSuccess =
                    authService.handleLogin(loginRequest.email(), loginRequest.password());
            if (loginSuccess == false) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid email or password, account is locked");
            }

            Authentication authenticationRequest =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequest.email(), loginRequest.password()));

            // 세션 생성
            SecurityContextHolder.getContext().setAuthentication(authenticationRequest);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            // 로그인 여부 쿠키 생성
            ResponseCookie loggedInCookie =
                    ResponseCookie.from("logged_in", "1")
                            .path("/")
                            .maxAge(24 * 60 * 60) // 24 hours
                            .sameSite("Lax")
                            .build();

            response.addHeader(HttpHeaders.SET_COOKIE, loggedInCookie.toString());

            UserPrincipal userPrincipal = (UserPrincipal) authenticationRequest.getPrincipal();
            UserResponse userResponse =
                    new UserResponse(
                            userPrincipal.getId(),
                            userPrincipal.getUsername(),
                            userPrincipal.getEmail(),
                            userPrincipal.IsVerified());

            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Authentication failed: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> postRegister(@RequestBody RegisterRequest registerRequest) {
        try {
            String encodedPassword = passwordEncoder.encode(registerRequest.password());
            boolean userAdded =
                    authService.addUser(
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

    @GetMapping("/me")
    public ResponseEntity<?> getMe(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            UserResponse userResponse =
                    new UserResponse(
                            userPrincipal.getId(),
                            userPrincipal.getUsername(),
                            userPrincipal.getEmail(),
                            userPrincipal.IsVerified());

            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/change-username")
    public ResponseEntity<?> changeUsername(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody ChangeUsernameRequest resetUsernameRequest) {
        try {
            authService.changeUsername(userPrincipal.getId(), resetUsernameRequest.newUsername());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            authService.deleteUser(userPrincipal.getId());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> getVerify(@RequestParam String token) {
        try {
            authService.verifyUserEmail(token);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        try {
            authService.requestPasswordReset(forgotPasswordRequest.email());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        try {
            String encodedPassword = passwordEncoder.encode(resetPasswordRequest.newPassword());
            authService.resetPassword(resetPasswordRequest.token(), encodedPassword);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
