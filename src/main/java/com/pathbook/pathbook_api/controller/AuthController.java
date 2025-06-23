package com.pathbook.pathbook_api.controller;

import com.pathbook.pathbook_api.dto.UserInfoDto;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.dto.request.ChangePasswordRequest;
import com.pathbook.pathbook_api.dto.request.DeleteUserRequest;
import com.pathbook.pathbook_api.dto.request.ForgotPasswordRequest;
import com.pathbook.pathbook_api.dto.request.LoginRequest;
import com.pathbook.pathbook_api.dto.request.RegisterRequest;
import com.pathbook.pathbook_api.dto.request.ResetPasswordRequest;
import com.pathbook.pathbook_api.dto.response.LoginResponse;
import com.pathbook.pathbook_api.dto.response.UserInfoResponse;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.service.AuthService;
import com.pathbook.pathbook_api.service.UserService;

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
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired private AuthenticationManager authManager;

    @Autowired private AuthService authService;

    @Autowired private UserService userService;

    /**
     * 로그인 요청을 처리합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /auth/login [POST]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @param requestBody {@link LoginRequest}
     * @param request
     * @param response
     * @return 로그인한 사용자 정보
     * @see {@link LoginRequest}
     * @see {@link LoginResponse}
     */
    @PostMapping("/login")
    public ResponseEntity<?> postLogin(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody LoginRequest requestBody) {
        String email = requestBody.email();
        String password = requestBody.password();
        String clientIp = extractClientIp(request);

        try {
            // 사용자 인증 처리
            Authentication authRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(email, password);
            Authentication authResponse = authManager.authenticate(authRequest);

            // 로그인 성공 처리
            authService.handleLoginSuccess(email, clientIp);

            // 세션 생성
            SecurityContextHolder.getContext().setAuthentication(authResponse);
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

            UserPrincipal userPrincipal =
                    (UserPrincipal)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User user = userService.fromPrincipal(userPrincipal);

            return new ResponseEntity<>(
                    LoginResponse.success(new UserInfoResponse(user)), HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            // 사용자 로그인 정보 불일치
            authService.handleLoginFail(email);

            return new ResponseEntity<>(
                    LoginResponse.invalidCredentials(), HttpStatus.UNAUTHORIZED);
        } catch (LockedException ex) {
            // 사용자 계정 잠금
            UserPrincipal userPrincipal = authService.loadUserPrincipal(email);

            return new ResponseEntity<>(
                    LoginResponse.locked(userPrincipal.getAccountLockedUntil()),
                    HttpStatus.UNAUTHORIZED);
        } catch (DisabledException ex) {
            // 사용자 계정 비활성화, 조건은 UserPrincipal.isEnabled() 참고
            UserPrincipal userPrincipal = authService.loadUserPrincipal(email);

            switch (userPrincipal.getAccountStatus()) {
                case UNVERIFIED:
                    return new ResponseEntity<>(
                            LoginResponse.emailUnverified(), HttpStatus.UNAUTHORIZED);

                case BANNED:
                    return new ResponseEntity<>(
                            LoginResponse.banned(
                                    userPrincipal.getBannedUntil(),
                                    userPrincipal.getBannedReason()),
                            HttpStatus.UNAUTHORIZED);

                case DISABLED:
                    return new ResponseEntity<>(LoginResponse.disabled(), HttpStatus.UNAUTHORIZED);

                case DELETED:
                    return new ResponseEntity<>(
                            LoginResponse.deleted(userPrincipal.getDeletedAt()),
                            HttpStatus.UNAUTHORIZED);

                default:
                    return new ResponseEntity<>(
                            LoginResponse.error("Unknown authentication error"),
                            HttpStatus.UNAUTHORIZED);
            }
        }
    }

    /**
     * 회원가입 요청을 처리합니다.
     *
     * <p>사용자 추가 후 이메일 인증을 위한 이메일을 송신합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /auth/register [POST]}
     *   <li>응답: {@code 201 Created}
     * </ul>
     *
     * @param requestBody {@link RegisterRequest}
     * @return 생성된 사용자
     * @see {@link RegisterRequest}
     * @see {@link UserInfoResponse}
     */
    @PostMapping("/register")
    public ResponseEntity<?> postRegister(@RequestBody RegisterRequest requestBody) {
        UserInfoDto createdUserInfo =
                authService.addUser(
                        requestBody.id(),
                        requestBody.username(),
                        requestBody.email(),
                        requestBody.password());

        authService.sendVerificationEmail(createdUserInfo.getEmail(), requestBody.password());

        return new ResponseEntity<>(new UserInfoResponse(createdUserInfo), HttpStatus.CREATED);
    }

    /**
     * 로그인 중인 세션으로부터 사용자 정보를 반환합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /auth/me [GET]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @param userPrincipal
     * @return 세션에 로그인 중인 사용자
     * @see {@link UserPrincipal}
     * @see {@link UserInfoResponse}
     */
    @GetMapping("/me")
    public ResponseEntity<?> getMe(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.fromPrincipal(userPrincipal);

        return new ResponseEntity<>(new UserInfoResponse(user), HttpStatus.OK);
    }

    /**
     * 이메일 인증을 위한 이메일을 송신합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /auth/send-verification-email [POST]}
     *   <li>응답: {@code 204 NO_CONTENT}
     * </ul>
     *
     * @param token JWT 토큰 문자열
     * @return
     */
    @PostMapping("/send-verification-email")
    public ResponseEntity<?> postSendVerificationEmail(@RequestBody LoginRequest requestBody) {
        authService.sendVerificationEmail(requestBody.email(), requestBody.password());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 이메일을 인증합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /auth/verify-email [POST]}
     *   <li>응답: {@code 204 NO_CONTENT}
     * </ul>
     *
     * @param token JWT 토큰 문자열
     * @return
     */
    @PostMapping("/verify-email")
    public ResponseEntity<?> postVerifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);

        // TODO: redirect to success page?
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 비밀번호 재설정 요청을 처리합니다. 비밀번호 재설정이 가능한 URL을 이메일로 전송합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /auth/forgot-password [POST]}
     *   <li>응답: {@code 204 NO_CONTENT}
     * </ul>
     *
     * @param requestBody {@link ForgotPasswordRequest}
     * @return
     * @see {@link ForgotPasswordRequest}
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> postForgotPassword(@RequestBody ForgotPasswordRequest requestBody) {
        authService.sendResetPasswordEmail(requestBody.email());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 비밀번호를 재설정합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /auth/reset-password [PUT]}
     *   <li>응답: {@code 204 NO_CONTENT}
     * </ul>
     *
     * @param requestBody {@link ResetPasswordRequest}
     * @return
     * @see {@link ResetPasswordRequest}
     */
    @PutMapping("/reset-password")
    public ResponseEntity<?> putResetPassword(@RequestBody ResetPasswordRequest requestBody) {
        authService.resetPassword(requestBody.token(), requestBody.newPassword());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 사용자의 비밀번호를 변경합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /auth/change-password [PUT]}
     *   <li>응답: {@code 204 NO_CONTENT}
     * </ul>
     *
     * @param userPrincipal
     * @param requestBody
     * @return
     * @see {@link ChangePasswordRequest}
     */
    @PutMapping("/change-password")
    public ResponseEntity<?> putChangePassword(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody ChangePasswordRequest requestBody) {
        authService.changePassword(
                userPrincipal.getId(), requestBody.oldPassword(), requestBody.newPassword());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 사용자를 삭제합니다.
     *
     * <p>삭제 후 세션 연결을 종료합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /auth/delete-user [DELETE]}
     *   <li>응답: {@code 204 NO_CONTENT}
     * </ul>
     *
     * @param userPrincipal
     * @param requestBody
     * @return
     * @see {@link DeleteUserRequest}
     */
    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteDeleteUser(
            HttpSession session,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody DeleteUserRequest requestBody) {
        authService.deleteUser(userPrincipal.getId(), requestBody.password());

        session.invalidate();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * HTTP 요청으로부터 클라이언트의 IP 주소를 추출합니다.
     *
     * @param request
     * @return 클라이언트의 IP 주소
     */
    private String extractClientIp(HttpServletRequest request) {
        // 표준 프록시 헤더
        String clientIp = request.getHeader("X-Forwarded-For");

        // 프록시 헤더
        if (isIpUnknown(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }

        // WebLogic 전용
        if (isIpUnknown(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }

        // 레거시 헤더
        if (isIpUnknown(clientIp)) {
            clientIp = request.getHeader("HTTP_CLIENT_IP");
        }

        // 레거시 헤더
        if (isIpUnknown(clientIp)) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        // 다이렉트 IP
        if (isIpUnknown(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        // 여러개의 IP 주소가 주어진 경우 가장 첫 IP만 반환
        if (clientIp != null && clientIp.contains(",")) {
            clientIp = clientIp.split(",")[0].trim();
        }

        return clientIp;
    }

    private boolean isIpUnknown(String ip) {
        return ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip);
    }
}
