package ru.zheleznov.api.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

	@Email(message = "Not valid email")
	@NotNull(message = "Email can't be null")
	@NotBlank(message = "Email can't be empty")
	private String email;

	@NotNull(message = "Password can't be null")
	@NotBlank(message = "Password can't be empty")
	private String password;
}
