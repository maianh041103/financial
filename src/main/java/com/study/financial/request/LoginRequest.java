package com.study.financial.request;

import com.study.financial.messages.AuthMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = AuthMessage.EMAIL_NULL)
    private String email;

    @NotBlank(message = AuthMessage.PASSWORD_NULL)
    private String password;
}
