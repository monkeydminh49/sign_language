package com.ptit.sign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreWithSubjectResponse implements Serializable {
    private boolean status;
    private String message;
    private List<UserScoreResponse> scoreList;
}
