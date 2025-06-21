package com.pathbook.pathbook_api.service;

import com.pathbook.pathbook_api.dto.FileMetaDto;
import com.pathbook.pathbook_api.dto.UserInfoDto;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.dto.response.UserProfileResponse;
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
     * 사용자 ID로부터 엔티티를 반환합니다.
     *
     * @param {@link UserInfoDto} userDto
     * @return {@link User} 엔티티
     */
    public User fromUserId(String userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> UserNotFoundException.withUserId(userId));
    }

    /**
     * 사용자 인증 정보로부터 엔티티를 반환합니다.
     *
     * @param {@link UserPrincipal} userPrincipal
     * @return {@link User} 엔티티
     */
    public User fromPrincipal(UserPrincipal userPrincipal) {
        return fromUserId(userPrincipal.getId());
    }

    /**
     * {@link UserInfoDto}로부터 엔티티를 반환합니다.
     *
     * @param {@link UserInfoDto} userDto
     * @return {@link User} 엔티티
     */
    public User fromDto(UserInfoDto userDto) {
        return fromUserId(userDto.getId());
    }

    /**
     * 사용자 프로필 정보를 반환합니다.
     *
     * @param userId
     * @return
     */
    public UserProfileResponse getUserProfile(String userId) {
        User user = fromUserId(userId);

        return new UserProfileResponse(user);
    }

    /**
     * 사용자의 프로필 정보를 수정합니다.
     *
     * @param userId
     * @param userData
     * @return {@link UserInfoDto} 수정된 사용자 정보
     */
    public UserInfoDto updateUserData(String userId, UserInfoDto userData) {
        User user = fromUserId(userId);

        user.setUserData(userData);

        userRepository.save(user);

        return new UserInfoDto(user);
    }

    /**
     * 사용자의 프로필 아이콘을 수정합니다.
     *
     * @param userId
     * @param iconFile
     * @return {@link FileMetaDto} 수정된 아이콘 정보
     */
    public FileMetaDto updateUserIcon(String userId, MultipartFile iconFile) {
        User user = fromUserId(userId);

        FileMetaDto storedFileMeta = storageService.store(iconFile, userId);
        File storedfileEntity = fileRepository.findById(storedFileMeta.getFilename()).get();

        user.setIconFile(storedfileEntity);

        userRepository.save(user);

        return storedFileMeta;
    }

    /**
     * 사용자의 프로필 배너를 수정합니다.
     *
     * @param userId
     * @param bannerFile
     * @return {@link FileMetaDto} 수정된 배너 정보
     */
    public FileMetaDto updateUserBanner(String userId, MultipartFile bannerFile) {
        User user = fromUserId(userId);

        FileMetaDto storedFileMeta = storageService.store(bannerFile, userId);
        File storedfileEntity = fileRepository.findById(storedFileMeta.getFilename()).get();

        user.setBannerFile(storedfileEntity);

        userRepository.save(user);

        return storedFileMeta;
    }
}
