package com.study.financial.service;

import com.study.financial.entity.User;
import com.study.financial.messages.AuthMessage;
import com.study.financial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public User getUserInfo(Jwt jwt){
        String email = jwt.getSubject();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(AuthMessage.USER_NOT_FOUND));
    }
}
