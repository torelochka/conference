package ru.zheleznov.api.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zheleznov.api.validations.annotations.PasswordsMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordsMatch
public class SignUpForm {

    @Email(message = "Not valid email")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = "Password too simple")
    private String password;

    @NotNull(message = "PasswordAgain can't be null")
    @NotEmpty(message = "PasswordAgain can't be empty")
    private String passwordAgain;
}
