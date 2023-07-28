package com.ptit.demo.config;

import com.ptit.demo.user.Role;
import com.ptit.demo.user.UserInfo;
import com.ptit.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class UserInforConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner commandLineRunner(
            UserRepository repository
    ) {
        return args -> {
            UserInfo admin = new UserInfo(
                    1L,
                    "Minh",
                    "minh@minh.com",
                    passwordEncoder.encode("123456"),
                    List.of(Role.ADMIN)
            );

            UserInfo user = new UserInfo(
                    2L,
                    "ndm",
                    "ndm@minh.com",
                    passwordEncoder.encode("123456"),
                    List.of(Role.USER)
            );

            repository.saveAll(List.of(admin, user));
            repository.save(admin);
        };
    }
}
