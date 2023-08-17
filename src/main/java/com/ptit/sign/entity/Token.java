package com.ptit.sign.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
public class Token {
    @Id
    @GeneratedValue
    private Long id;
    private String token;
    private boolean expired;
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
