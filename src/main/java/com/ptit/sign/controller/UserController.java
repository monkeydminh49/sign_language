package com.ptit.sign.controller;

import com.ptit.sign.dto.MappingResponse;
import com.ptit.sign.service.UserService;
import com.ptit.sign.entity.User;
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public MappingResponse getUsers() {
        List<User> users = userService.getAllUsers();
        return MappingResponse.builder()
                .status("ok")
                .body(users)
                .message("Get users successfully")
                .build();
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public MappingResponse getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return MappingResponse.builder()
                .status("ok")
                .body(user)
                .message("Get user with id = " +  id + " successfully")
                .build();
    }
}
