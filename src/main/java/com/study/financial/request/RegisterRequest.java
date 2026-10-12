package com.study.financial.request;

import com.study.financial.messages.AuthMessage;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull(message = AuthMessage.EMAIL_NULL)
    private String email;

    @NotNull(message = AuthMessage.FULL_NAME_NULL)
    private String fullName;

    @NotNull(message = AuthMessage.PASSWORD_NULL)
    private String password;

    @NotNull(message = AuthMessage.RE_PASSWORD_NULL)
    private String rePassword;
}
