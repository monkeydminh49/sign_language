package com.ptit.demo.entity;

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
        name = "LABELS"
//        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})}
)
public class Label {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(unique = true)
    private String labelVn;
    private String labelEn;
    private long subjectId;
    private long levelId;
}

