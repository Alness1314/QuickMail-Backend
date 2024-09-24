package com.alness.quickmail.users.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alness.quickmail.users.dto.UserResponse;
import com.alness.quickmail.users.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${api.prefix}/users")
@Tag(name = "Users", description = ".")
public class UsersController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserResponse>> findAll(@RequestParam Map<String, String> parameters) {
        List<UserResponse> response = userService.find(parameters);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findOne(@PathVariable String id) {
        UserResponse response = userService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
