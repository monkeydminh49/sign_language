package com.ptit.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserInfoDetailsService userService;

    @GetMapping("/user")
    public List<UserInfo> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public UserInfo registerUser(@RequestBody UserInfo userInfo) {
        return userService.registerUser(userInfo);
    }
}
