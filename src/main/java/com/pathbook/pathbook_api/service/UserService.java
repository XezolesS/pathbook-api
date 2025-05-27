package com.pathbook.pathbook_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pathbook.pathbook_api.dto.ImageDto;
import com.pathbook.pathbook_api.dto.UserResponse;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.exception.UserNotFoundException;
import com.pathbook.pathbook_api.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with id: " + userId));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isVerified());
    }

    public ImageDto getUserIcon(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with id: " + userId));

        return new ImageDto(user.getIcon(), user.getIconContentType());
    }

    public ImageDto getUserBanner(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with id: " + userId));

        return new ImageDto(user.getBanner(), user.getBannerContentType());
    }

    public void updateUserIcon(String userId, byte[] iconBytes, String iconContentType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with id: " + userId));
        
        user.setIcon(iconBytes);
        user.setIconContentType(iconContentType);

        userRepository.save(user);
    }
    
    public void updateUserBanner(String userId, byte[] bannerBytes, String bannerContentType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with id: " + userId));
        
        user.setBanner(bannerBytes);
        user.setBannerContentType(bannerContentType);
    
        userRepository.save(user);
    }

}
