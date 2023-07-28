package com.ptit.demo.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@Table(
//        name = "user_info",
//        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})}
)
public class UserInfo {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(unique = true)
    private String name;
    private String email;
    private String password;

    private List<Role> roles;

}

