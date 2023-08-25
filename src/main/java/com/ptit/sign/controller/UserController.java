package com.ptit.sign.controller;

import com.ptit.sign.dto.MappingResponse;
import com.ptit.sign.dto.UpdateUserRequest;
import com.ptit.sign.dto.UserResponse;
import com.ptit.sign.entity.User;
import com.ptit.sign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

        List<UserResponse> userResponses = users.stream().map(user -> UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .dateOfBirth(user.getDateOfBirth())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .language(user.getLanguage())
                .supportedBy(user.getSupportedBy())
                .registerType(user.getRegisterType())
                .build()).toList();

        return MappingResponse.builder()
                .status("ok")
                .body(userResponses)
                .message("Get users successfully")
                .build();
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public MappingResponse getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .dateOfBirth(user.getDateOfBirth())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .language(user.getLanguage())
                .supportedBy(user.getSupportedBy())
                .registerType(user.getRegisterType())
                .build();
        return MappingResponse.builder()
                .status("ok")
                .body(userResponse)
                .message("Get user with id = " +  id + " successfully")
                .build();
    }

    @PutMapping("/user")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public MappingResponse updateUser(Principal principal, @RequestBody UpdateUserRequest updateUserRequest) {
        String email = principal.getName();
        User updatedUser = userService.updateUser(email, updateUserRequest);

        UserResponse userResponse = UserResponse.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .roles(updatedUser.getRoles())
                .dateOfBirth(updatedUser.getDateOfBirth())
                .address(updatedUser.getAddress())
                .phoneNumber(updatedUser.getPhoneNumber())
                .language(updatedUser.getLanguage())
                .supportedBy(updatedUser.getSupportedBy())
                .registerType(updatedUser.getRegisterType())
                .build();

        return MappingResponse.builder()
                .status("ok")
                .body(userResponse)
                .message("Update user with id = " +  updatedUser.getId() + " successfully")
                .build();
    }
}
