package com.example.habtra.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public Principal currentUserName(Principal principal) {
        return principal;
    }

    @PostMapping(value = "/users")
    public User addUser(@RequestBody User user) {
        return this.userService.create(user);
    }
}
