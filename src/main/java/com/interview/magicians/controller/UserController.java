package com.interview.magicians.controller;

import com.interview.magicians.dto.UserDto;
import com.interview.magicians.entity.User;
import com.interview.magicians.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserDto getCurrentUser(@AuthenticationPrincipal User user) {
        return UserDto.builder().username(user.getUsername()).health(user.getHealth()).build();
    }
}
