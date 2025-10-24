package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.config.Helper;
import com.example.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    // private final List<String> users;
    private final Helper helper;
    private final UserService userService;

    public UserController(List<String> users, Helper helper, UserService userService) {
        // this.users = users;
        this.helper = helper;
        this.userService = userService;
    }

    // @GetMapping
    // public List<User> getUsers() {
    // return users.getAllUsers();
    // }

    // @PostMapping
    // public User addUser(@RequestParam String name) {
    // return users.saveUser(name);
    // }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username,
            @RequestParam String password) {

        String token = this.userService.login(username, password);

        if (token == null) {
            return new ResponseEntity<>(helper.buildResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials", null),
                    HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(helper.buildResponse(HttpStatus.OK, "Success", token),
                HttpStatus.OK);
    }
}
