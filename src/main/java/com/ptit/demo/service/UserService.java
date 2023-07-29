package com.ptit.demo.service;

import com.ptit.demo.component.UserInfoUserDetails;
import com.ptit.demo.dto.JwtResponse;
import com.ptit.demo.dto.RegisterRequest;
import com.ptit.demo.entity.User;
import com.ptit.demo.repository.UserRepository;
import com.ptit.demo.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtService jwtService;
    public JwtResponse register(RegisterRequest request) {

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(UserRole.ROLE_USER)
                .build();

        User userExists = repository.findByEmail(user.getEmail()).orElse(null);
        if (userExists != null) {
            throw new RuntimeException("user already exists");
        }

//        user.setPassword(encoder.encode(user.getPassword()));

        repository.save(user);

        UserInfoUserDetails userDetails = new UserInfoUserDetails(user);
        String token = jwtService.generateToken(userDetails);

        return JwtResponse.builder()
                .token(token)
                .build();
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
    }
}
