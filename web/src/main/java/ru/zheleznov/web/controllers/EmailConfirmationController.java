package ru.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.zheleznov.api.dto.MessageResponse;
import ru.zheleznov.api.services.UserService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Controller
public class EmailConfirmationController {

    private final UserService userService;

    @Autowired
    public EmailConfirmationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/confirmed/{code}")
    public ResponseEntity<?> confirmEmail(@PathVariable @NotBlank @Size(max = 36) String code) {
        try {
            UUID id = UUID.fromString(code);
            userService.confirmEmail(id);
            return ResponseEntity.ok().body(new MessageResponse("Email confirmed"));
        }
        catch (IllegalArgumentException exception) {
            return ResponseEntity.status(409).body(new MessageResponse("Can't read confirmed code"));
        }
    }
}
