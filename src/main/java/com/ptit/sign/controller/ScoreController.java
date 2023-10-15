package com.ptit.sign.controller;

import com.ptit.sign.dto.MappingResponse;
import com.ptit.sign.dto.ScoreWithSubjectResponse;
import com.ptit.sign.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/scoreWithSubject")
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
}
