package ru.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zheleznov.api.dto.UserDto;
import ru.zheleznov.api.forms.LoginRequest;
import ru.zheleznov.api.forms.SignUpForm;
import ru.zheleznov.api.forms.SignUpRequest;
import ru.zheleznov.api.services.JwtLoginService;
import ru.zheleznov.api.services.SignUpService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final SignUpService signUpService;
  private final JwtLoginService jwtLoginService;

  @Autowired
  public AuthController(SignUpService signUpService, JwtLoginService jwtLoginService) {
    this.signUpService = signUpService;
    this.jwtLoginService = jwtLoginService;
  }

  @PostMapping("/signIn")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(jwtLoginService.signIn(loginRequest));
  }

  @PostMapping("/signUp")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

    SignUpForm signUpForm = SignUpForm.builder()
            .email(signUpRequest.getEmail())
            .password(signUpRequest.getPassword())
            .passwordAgain(signUpRequest.getPassword())
            .build();

    UserDto userDto = signUpService.signUp(signUpForm);
    return ResponseEntity.ok(userDto);
  }

}
