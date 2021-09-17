package ru.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zheleznov.api.dto.MessageResponse;
import ru.zheleznov.api.services.UserService;

@RestController
@RequestMapping("/user/")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> userDelete(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok(new MessageResponse("Success"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateListenerToSpeaker(@PathVariable Long id) {
         userService.updateUser(id);
        return ResponseEntity.ok(new MessageResponse("Success"));
    }
}
