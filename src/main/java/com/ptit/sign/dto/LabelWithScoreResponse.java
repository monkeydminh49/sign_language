package com.ptit.sign.dto;

import jakarta.persistence.Column;
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
public class LabelWithScoreResponse {
    private Long id;
    private String labelVn;
    private String labelEn;
    private long subjectId;
    private long levelId;
    private Float latestScore;
}
