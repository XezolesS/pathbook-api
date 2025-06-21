package com.pathbook.pathbook_api.controller;

import com.pathbook.pathbook_api.dto.UserInfo;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.dto.request.UserRequest;
import com.pathbook.pathbook_api.dto.response.UserResponse;
import com.pathbook.pathbook_api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired private UserService userService;

    /**
     * 사용자 정보를 불러옵니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /user/get/{userId} [GET]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @param userId
     * @return
     */
    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        UserResponse userResponse = userService.getUser(userId);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    /**
     * 사용자 정보를 수정합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /user/update-data [GET]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @param userPrincipal
     * @param entity
     * @return 수정된 사용자 정보
     */
    @PutMapping("/update-data")
    public ResponseEntity<?> putUpdateUserData(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody UserRequest requestBody) {
        UserResponse userResponse =
                userService.updateUserData(userPrincipal.getId(), (UserInfo) requestBody);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
