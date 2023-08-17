package com.ptit.sign.entity;

import com.ptit.sign.user.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@Table(
        name = "USERS"
//        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})}
)
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private List<UserRole> roles;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

}

