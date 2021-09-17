package ru.zheleznov.api.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {

    @Email(message = "Not valid email")
    @NotNull(message = "Email can't be null")
    @NotBlank(message = "Email can't be empty")
    private String email;

    @NotNull(message = "Password can't be null")
    @NotBlank(message = "Password can't be empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = "Password too simple")
    private String password;
}
