package com.study.financial.request;

import com.study.financial.messages.AuthMessage;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull(message = AuthMessage.EMAIL_NULL)
    private String email;

    @NotNull(message = AuthMessage.PASSWORD_NULL)
    private String password;
}
