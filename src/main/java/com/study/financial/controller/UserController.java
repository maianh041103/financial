package com.study.financial.controller;

import com.study.financial.messages.UserMessage;
import com.study.financial.response.ApiResponse;
import com.study.financial.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getUserInfo(
            @AuthenticationPrincipal Jwt jwt
    ){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), userService.getUserInfo(jwt), UserMessage.GET_USER_INFO_SUCCESS));
    }
}
