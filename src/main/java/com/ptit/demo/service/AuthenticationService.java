package com.ptit.demo.service;

import com.ptit.demo.component.UserInfoUserDetails;
import com.ptit.demo.dto.JwtResponse;
import com.ptit.demo.dto.LoginRequest;
import com.ptit.demo.dto.RefreshTokenRequest;
import com.ptit.demo.dto.RegisterRequest;
import com.ptit.demo.entity.User;
import com.ptit.demo.repository.UserRepository;
import com.ptit.demo.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/v1")
public class AuthenticationService {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    public JwtResponse register(RegisterRequest request) {

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .roles(List.of(UserRole.ROLE_USER))
                .build();

        User userExists = repository.findByEmail(user.getEmail()).orElse(null);
        if (userExists != null) {
            throw new RuntimeException("user already exists");
        }

        repository.save(user);

        UserInfoUserDetails userDetails = new UserInfoUserDetails(user);
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
            return JwtResponse.builder()
                    .accessToken(jwtService.generateToken(userDetails))
                    .refreshToken(jwtService.generateRefreshToken(userDetails))
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid login request! Please check the your username and password");
        }
    }

    public JwtResponse refreshToken(RefreshTokenRequest request) {

        if (jwtService.isTokenExpired(request.getToken())) {
            throw new RuntimeException("refresh token is expired! Please login again");
        }

        String username = jwtService.extractUsername(request.getToken());
        UserInfoUserDetails userDetails = repository.findByEmail(username).map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with username: " + username));

        return JwtResponse.builder()
                .accessToken(jwtService.generateToken(userDetails))
                .refreshToken(request.getToken())
                .build();
    }
}
