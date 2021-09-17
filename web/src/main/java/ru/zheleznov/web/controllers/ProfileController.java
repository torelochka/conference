package ru.zheleznov.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.zheleznov.api.dto.MessageResponse;
import ru.zheleznov.api.dto.UserDto;
import ru.zheleznov.api.services.UserService;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.utils.JwtUtils;

import java.util.List;

@RestController
public class ProfileController {

    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public ProfileController(JwtUtils jwtUtils, ModelMapper modelMapper, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestParam("access_token") String token) {
        if (jwtUtils.validateJwtToken(token)){
            User userFromJwtToken = jwtUtils.getUserFromJwtToken(token);

            UserDto userDto = modelMapper.map(userFromJwtToken, UserDto.class);
            userDto.setRole(userFromJwtToken.getRole().toString());

            return ResponseEntity.ok(userDto);
        }

        return ResponseEntity.status(409).body(new MessageResponse("Error: jwt not valid"));
    }

    @GetMapping("/speakers")
    public List<UserDto> getSpeakers() {
        return userService.getAllSpeakers();
    }

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
    }
}
