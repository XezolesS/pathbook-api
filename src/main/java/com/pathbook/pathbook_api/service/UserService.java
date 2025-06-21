package com.pathbook.pathbook_api.service;

import com.pathbook.pathbook_api.dto.FileMeta;
import com.pathbook.pathbook_api.dto.UserInfo;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.dto.response.UserProfileResponse;
import com.pathbook.pathbook_api.dto.response.UserResponse;
import com.pathbook.pathbook_api.entity.File;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.exception.UserNotFoundException;
import com.pathbook.pathbook_api.repository.FileRepository;
import com.pathbook.pathbook_api.repository.UserRepository;
import com.pathbook.pathbook_api.storage.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;

    @Autowired private FileRepository fileRepository;

    @Autowired private StorageService storageService;

    /**
     * 사용자 인증 정보로부터 엔티티를 반환합니다.
     *
     * @return {@link User} 엔티티
     */
    public User fromPrincipal(UserPrincipal userPrincipal) {
        return userRepository
                .findById(userPrincipal.getId())
                .orElseThrow(() -> UserNotFoundException.withUserId(userPrincipal.getId()));
    }

    /**
     * 사용자 프로필 정보를 반환합니다.
     *
     * @param userId
     * @return
     */
    public UserProfileResponse getUserProfile(String userId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> UserNotFoundException.withUserId(userId));

        return new UserProfileResponse(user);
    }

    /**
     * 사용자의 프로필 정보를 수정합니다.
     *
     * @param userId
     * @param userData
     * @return {@link UserResponse} 수정된 사용자 정보
     */
    public UserResponse updateUserData(String userId, UserInfo userData) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> UserNotFoundException.withUserId(userId));

        user.setUserData(userData);

        userRepository.save(user);

        return new UserResponse(user);
    }

    /**
     * 사용자의 프로필 아이콘을 수정합니다.
     *
     * @param userId
     * @param iconFile
     * @return {@link FileMeta} 수정된 아이콘 정보
     */
    public FileMeta updateUserIcon(String userId, MultipartFile iconFile) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> UserNotFoundException.withUserId(userId));

        FileMeta storedFileMeta = storageService.store(iconFile, userId);

        File fileEntity;
        if (storedFileMeta instanceof File) {
            fileEntity = (File) storedFileMeta;
        } else {
            fileEntity = fileRepository.findById(storedFileMeta.getFilename()).get();
        }

        user.setIconFile(fileEntity);

        userRepository.save(user);

        return storedFileMeta;
    }

    /**
     * 사용자의 프로필 배너를 수정합니다.
     *
     * @param userId
     * @param bannerFile
     * @return {@link FileMeta} 수정된 배너 정보
     */
    public FileMeta updateUserBanner(String userId, MultipartFile bannerFile) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> UserNotFoundException.withUserId(userId));

        FileMeta storedFileMeta = storageService.store(bannerFile, userId);

        File fileEntity;
        if (storedFileMeta instanceof File) {
            fileEntity = (File) storedFileMeta;
        } else {
            fileEntity = fileRepository.findById(storedFileMeta.getFilename()).get();
        }

        user.setBannerFile(fileEntity);

        userRepository.save(user);

        return storedFileMeta;
    }
}
