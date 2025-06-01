package com.pathbook.pathbook_api.service;

import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class PathbookUserDetailsService implements UserDetailsService {
    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // username은 이메일로 사용함
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        return new UserPrincipal(user);
    }
}
