package com.ptit.sign.controller;

import com.ptit.sign.component.UserInfoUserDetails;
import com.ptit.sign.dto.MappingResponse;
import com.ptit.sign.dto.ScoreWithSubjectResponse;
import com.ptit.sign.entity.User;
import com.ptit.sign.entity.UserScore;
import com.ptit.sign.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/scoreWithSubject")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public MappingResponse scoreWithSubject(
            @RequestParam(name="levelIds", required = false) String levelIds,
            @RequestParam(name="subjectIds", required = false) String subjectIds
    ){
        ScoreWithSubjectResponse scoreResponse = scoreService.scoreWithSubject(levelIds, subjectIds);
        return MappingResponse.builder()
                .status("ok")
                .body(scoreResponse)
                .message("")
                .build();
    }

    @PostMapping("/postUserScore")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public MappingResponse postUserScore(
            @RequestParam(name="labelId") Long labelId,
            @RequestParam(name="score") float score,
            Authentication authentication
    ){
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        UserScore userScore = scoreService.postUserScore(user.getId(), labelId, score);

        return MappingResponse.builder()
                .status("ok")
                .body(userScore)
                .message("")
                .build();
    }

    @GetMapping("/topUserScoresOfLabel")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public MappingResponse getTopUserScoresOfLabel(
            @RequestParam(name="labelId") Long labelId,
            @RequestParam(name="top", required = false, defaultValue = "5") int top
    ){
        List<UserScore> userScores = scoreService.getTopUserScoresOfLabel(labelId, top);

        return MappingResponse.builder()
                .status("ok")
                .body(userScores)
                .message("")
                .build();
    }
}
