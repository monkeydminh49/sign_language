package com.ptit.sign.controller;

import com.ptit.sign.dto.MappingResponse;
import com.ptit.sign.entity.Label;
import com.ptit.sign.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/v1")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping("/label")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public MappingResponse getLabelsByLevelsAndSubjects(
            @RequestParam(name="levelId", required = false) String levelId,
            @RequestParam(name="subjectId", required = false) String subjectId
    ) {
        List<Label> labels = labelService.getLabelsByLevelsAndSubjects(levelId, subjectId);
        return MappingResponse.builder()
                .status("ok")
                .body(labels)
                .message("Get labels successfully")
                .build();
    }
}
