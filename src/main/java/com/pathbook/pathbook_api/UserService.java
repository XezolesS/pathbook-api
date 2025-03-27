package com.pathbook.pathbook_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 로그인 검증
    public boolean authenticate(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    // 회원가입 처리
    public boolean registerUser(String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            return false; // 이메일 중복
        }

        UserEntity newUser = new UserEntity();
        newUser.setEmail(email);              // setEmail()으로 이메일 설정
        newUser.setPassword(password);        // setPassword()로 비밀번호 설정

        userRepository.save(newUser);         // UserEntity 저장
        return true;
    }
}
