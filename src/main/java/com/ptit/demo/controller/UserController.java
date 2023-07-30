package com.ptit.demo.controller;

import com.ptit.demo.dto.JwtResponse;
import com.ptit.demo.dto.RegisterRequest;
import com.ptit.demo.service.UserService;
import com.ptit.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
