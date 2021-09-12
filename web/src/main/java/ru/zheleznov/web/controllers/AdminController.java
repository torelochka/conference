package ru.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.zheleznov.api.dto.UserDeleteResult;
import ru.zheleznov.api.services.UserService;

@RestController
@RequestMapping("/user/action")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{id}/delete")
    public UserDeleteResult userDelete(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}/update")
    public void updateListenerToSpeaker(@PathVariable Long id) {
         userService.updateUser(id);
    }
}
