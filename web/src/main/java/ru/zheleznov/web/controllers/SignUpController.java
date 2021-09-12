package ru.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.zheleznov.api.dto.SignUpResult;
import ru.zheleznov.api.dto.UserDto;
import ru.zheleznov.api.forms.SignUpForm;
import ru.zheleznov.api.services.SignUpService;

import java.util.Optional;

@RestController
public class SignUpController {

    private final SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpForm signUpForm) {
        if (signUpForm.getPassword().equals(signUpForm.getPasswordAgain())) {
            return ResponseEntity.ok(signUpService.signUp(signUpForm));
        }
        return ResponseEntity.badRequest().body(SignUpResult.builder()
                .user(Optional.empty())
                .message("Пароли не совпадают")
                .build());
    }
}
