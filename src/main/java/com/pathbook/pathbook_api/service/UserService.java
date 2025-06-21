package com.pathbook.pathbook_api.service;

import com.pathbook.pathbook_api.dto.UserData;
import com.pathbook.pathbook_api.dto.response.UserResponse;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.exception.UserNotFoundException;
import com.pathbook.pathbook_api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;

    /**
     * 사용자 정보를 반환합니다.
     *
     * @param userId
     * @return
     */
    public UserResponse getUser(String userId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> UserNotFoundException.withUserId(userId));

        return new UserResponse(user);
    }

    public UserResponse updateUserData(String userId, UserData userData) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> UserNotFoundException.withUserId(userId));

        user.setUserData(userData);

        userRepository.save(user);

        return new UserResponse(user);
    }
}
