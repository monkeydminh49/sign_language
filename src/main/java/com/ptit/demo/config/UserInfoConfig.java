package com.ptit.demo.config;

import com.ptit.demo.entity.Token;
import com.ptit.demo.entity.User;
import com.ptit.demo.component.UserInfoUserDetails;
import com.ptit.demo.repository.UserRepository;
import com.ptit.demo.user.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class UserInfoConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            UserRepository repository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            User admin = new User(
                    1L,
                    "Minh",
                    "minh@minh.com",
                    passwordEncoder.encode("123456"),
                    List.of(UserRole.ROLE_USER),
                    List.of()
            );

            User user = new User(
                    2L,
                    "ndm",
                    "ndm@minh.com",
                    passwordEncoder.encode("123456"),
                    List.of(UserRole.ROLE_ADMIN),
                    List.of()
            );

//            System.out.println(new UserInfoUserDetails(admin).getAuthorities());

            repository.saveAll(List.of(admin, user));
        };
    }
}
