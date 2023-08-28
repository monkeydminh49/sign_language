package com.ptit.sign.controller;

import com.ptit.sign.dto.MappingResponse;
import com.ptit.sign.entity.Label;
import com.ptit.sign.entity.Level;
import com.ptit.sign.service.LabelService;
import com.ptit.sign.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
@RequestMapping("/api/v1")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private LevelService levelService;

    @GetMapping("/label")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public MappingResponse getLabelsByLevelIdsAndSubjectIds(
            @RequestParam(name="levelIds", required = false) String levelIds,
            @RequestParam(name="subjectIds", required = false) String subjectIds
    ) {
        List<Label> labels = labelService.getLabelsByLevelIdsAndSubjectIds(levelIds, subjectIds);
        return MappingResponse.builder()
                .status("ok")
                .body(labels)
                .message("Get labels successfully")
                .build();
    }

    @GetMapping("/list-labels-by-subjectId")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public MappingResponse getListLabelsBySubjectId(
            @RequestParam(name="subjectId") String subjectId
    ) {
        List<Level> levels = levelService.getLevels();
        List<List<Label>> lableList = new ArrayList<>();

        for (Level level : levels) {
            List<Label> labels = labelService.getLabelsByLevelIdsAndSubjectIds(level.getId().toString(), subjectId);
            lableList.add(labels);
        }

        return MappingResponse.builder()
                .status("ok")
                .body(lableList)
                .message("Get labels successfully")
                .build();
    }

}
