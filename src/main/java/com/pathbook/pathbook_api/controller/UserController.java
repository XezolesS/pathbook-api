package com.pathbook.pathbook_api.controller;

import com.pathbook.pathbook_api.dto.FileMetaDto;
import com.pathbook.pathbook_api.dto.UserInfoDto;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.dto.request.UserInfoRequest;
import com.pathbook.pathbook_api.dto.response.UserInfoResponse;
import com.pathbook.pathbook_api.dto.response.UserProfileResponse;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired private UserService userService;

    /**
     * 사용자 프로필 정보를 불러옵니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /user/profile/{userId} [GET]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @param userId
     * @return
     */
    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable String userId) {
        UserProfileResponse userProfileResponse = userService.getUserProfile(userId);

        return new ResponseEntity<>(userProfileResponse, HttpStatus.OK);
    }

    /**
     * 사용자 정보를 수정합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /user/update/data [PUT]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @param userPrincipal
     * @param entity
     * @return 수정된 사용자 정보
     */
    @PutMapping("/update/data")
    public ResponseEntity<?> putUpdateUserData(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody UserInfoRequest requestBody) {
        UserInfoDto userInfo =
                userService.updateUserData(userPrincipal.getId(), (UserInfoDto) requestBody);

        return new ResponseEntity<>(new UserInfoResponse(userInfo), HttpStatus.OK);
    }

    /**
     * 사용자의 아이콘을 수정합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /user/update/icon [PUT]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @param userPrincipal
     * @param entity
     * @return 업로드 된 아이콘
     */
    @PutMapping("/update/icon")
    public ResponseEntity<?> putUpdateUserIcon(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestPart(value = "iconFile", required = true) MultipartFile iconFile) {
        FileMetaDto fileMeta = userService.updateUserIcon(userPrincipal.getId(), iconFile);

        return new ResponseEntity<>(fileMeta, HttpStatus.OK);
    }

    /**
     * 사용자의 배너를 수정합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /user/update/icon [PUT]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @param userPrincipal
     * @param entity
     * @return 업로드 된 아이콘
     */
    @PutMapping("/update/banner")
    public ResponseEntity<?> putUpdateUserBanner(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestPart(value = "bannerFile", required = false) MultipartFile bannerFile) {
        FileMetaDto fileMeta = userService.updateUserBanner(userPrincipal.getId(), bannerFile);

        return new ResponseEntity<>(fileMeta, HttpStatus.OK);
    }
}
