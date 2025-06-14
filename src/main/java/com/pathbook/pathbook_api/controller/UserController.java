package com.pathbook.pathbook_api.controller;

import com.pathbook.pathbook_api.dto.ImageDto;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.exception.UserNotFoundException;
import com.pathbook.pathbook_api.response.UserResponse;
import com.pathbook.pathbook_api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        try {
            UserResponse userResponse = userService.getUser(userId);

            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}/icon")
    public ResponseEntity<?> getUserIcon(@PathVariable String userId) {
        try {
            ImageDto icon = userService.getUserIcon(userId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(icon.contentType()));

            return new ResponseEntity<>(icon.bytes(), headers, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}/banner")
    public ResponseEntity<?> getUserBanner(@PathVariable String userId) {
        try {
            ImageDto banner = userService.getUserBanner(userId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(banner.contentType()));

            return new ResponseEntity<>(banner.bytes(), headers, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/update-icon", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postUpdateIcon(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam MultipartFile file) {
        try {
            userService.updateUserIcon(
                    userPrincipal.getId(), file.getBytes(), file.getContentType());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/update-banner", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postUpdateBanner(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam MultipartFile file) {
        try {
            userService.updateUserBanner(
                    userPrincipal.getId(), file.getBytes(), file.getContentType());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
