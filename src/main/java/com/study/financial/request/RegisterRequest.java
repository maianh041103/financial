package com.study.financial.request;

import com.study.financial.messages.AuthMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = AuthMessage.EMAIL_NULL)
    @Email(message = AuthMessage.EMAIL_INVALID)
    private String email;

    @NotBlank(message = AuthMessage.FULL_NAME_NULL)
    private String fullName;

    @NotBlank(message = AuthMessage.PASSWORD_NULL)
    private String password;

    @NotBlank(message = AuthMessage.RE_PASSWORD_NULL)
    private String rePassword;
}
