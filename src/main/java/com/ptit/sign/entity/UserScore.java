package com.ptit.sign.entity;

import com.ptit.sign.entity.id.UserScoreId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@Table(
        name = "USER_SCORE"
)
@IdClass(UserScoreId.class)
public class UserScore {
    @Id
    private Long userId;

    @Id
    private Long labelId;

    private Float score;

    private Date actionDate;
}

