package com.ptit.sign.entity;

import com.ptit.sign.user.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;
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
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private String language;
    private String supportedBy;
    private String registerType;

}

