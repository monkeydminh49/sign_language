package com.ptit.demo.service;

import com.ptit.demo.component.UserInfoUserDetails;
import com.ptit.demo.dto.*;
import com.ptit.demo.entity.Token;
import com.ptit.demo.entity.User;
import com.ptit.demo.repository.TokenRepository;
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
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
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

        User userExists = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (userExists != null) {
            throw new RuntimeException("user already exists");
        }

        var savedUser = userRepository.save(user);

        UserInfoUserDetails userDetails = new UserInfoUserDetails(user);
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

//        saveUserToken(savedUser, accessToken);

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }



    public UserResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();

            var user = userRepository.findByEmail(request.getUsername())
                    .orElseThrow();

            String accessToken = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);

//            revokeAllUserTokens(user);
//            saveUserToken(user, accessToken);

           JwtResponse token = JwtResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

              return UserResponse.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .token(token)
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
        UserInfoUserDetails userDetails = userRepository.findByEmail(username).map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with username: " + username));

        return JwtResponse.builder()
                .accessToken(jwtService.generateToken(userDetails))
                .refreshToken(request.getToken())
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);

        });

        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String accessToken) {
        Token saveUserToken = Token.builder()
                .user(user)
                .token(accessToken)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(saveUserToken);

    }
}
