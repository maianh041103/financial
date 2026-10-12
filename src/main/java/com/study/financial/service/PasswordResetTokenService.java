package com.study.financial.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {
    private static final Logger logger =
            LoggerFactory.getLogger(PasswordResetTokenService.class);
}
