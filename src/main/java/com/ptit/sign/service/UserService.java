package com.ptit.sign.service;

import com.ptit.sign.dto.UpdateUserRequest;
import com.ptit.sign.entity.User;
import com.ptit.sign.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
    }

    public User updateUser(String email, UpdateUserRequest updateUserRequest) {
        var user = repository.findByEmail(email).orElseThrow(() -> new RuntimeException("user not found"));

        if (updateUserRequest.getName() != null){
            user.setName(updateUserRequest.getName());
        }
        if (updateUserRequest.getEmail() != null){
            user.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.getDateOfBirth() != null){
            user.setDateOfBirth(updateUserRequest.getDateOfBirth());
        }
        if (updateUserRequest.getAddress() != null){
            user.setAddress(updateUserRequest.getAddress());
        }
        if (updateUserRequest.getPhoneNumber() != null){
            user.setPhoneNumber(updateUserRequest.getPhoneNumber());
        }
        if (updateUserRequest.getLanguage() != null){
            user.setLanguage(updateUserRequest.getLanguage());
        }
        if (updateUserRequest.getSupportedBy() != null){
            user.setSupportedBy(updateUserRequest.getSupportedBy());
        }
        if (updateUserRequest.getRegisterType() != null){
            user.setRegisterType(updateUserRequest.getRegisterType());
        }


        return repository.save(user);
    }
}
