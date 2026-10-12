package com.study.financial.controller;

import com.study.financial.messages.AuthMessage;
import com.study.financial.request.LoginRequest;
import com.study.financial.request.RegisterRequest;
import com.study.financial.response.ApiResponse;
import com.study.financial.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.CREATED.value(), authService.register(request), AuthMessage.REGISTER_SUCCESS)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request){
        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(), authService.login(request), AuthMessage.LOGIN_SUCCESS)
        );
    }

}
