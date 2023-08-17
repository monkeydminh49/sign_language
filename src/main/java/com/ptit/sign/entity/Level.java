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
@Table(
        name = "LEVELS"
//        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})}
)
public class Level {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(unique = true)
    private String name;

}

