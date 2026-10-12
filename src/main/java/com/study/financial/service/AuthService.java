package com.study.financial.service;

import com.study.financial.entity.User;
import com.study.financial.enums.StatusEnum;
import com.study.financial.exception.BusinessException;
import com.study.financial.messages.AuthMessage;
import com.study.financial.repository.UserRepository;
import com.study.financial.request.LoginRequest;
import com.study.financial.request.RegisterRequest;
import com.study.financial.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final static Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void validateRegister(RegisterRequest request){
        if(StringUtils.isBlank(request.getEmail())) throw new BusinessException(AuthMessage.EMAIL_NULL, HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(request.getFullName())) throw new BusinessException(AuthMessage.FULL_NAME_NULL, HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(request.getPassword())) throw new BusinessException(AuthMessage.PASSWORD_NULL, HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(request.getRePassword())) throw new BusinessException(AuthMessage.RE_PASSWORD_NULL, HttpStatus.BAD_REQUEST);
        if(!request.getPassword().equals(request.getRePassword())) throw new BusinessException(AuthMessage.PASSWORD_NOT_EQUAL, HttpStatus.BAD_REQUEST);
    }

    public User register(RegisterRequest request){
        log.info("Start register");
        validateRegister(request);
        return userRepository.save(com.study.financial.entity.User.builder()
                        .email(request.getEmail())
                        .fullName(request.getFullName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .status(StatusEnum.ACTIVE)
                .build());
    }

    public String login(LoginRequest request){
        log.info("Start login");
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(AuthMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BusinessException(AuthMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return jwtService.generateToken(user);
    }
}
