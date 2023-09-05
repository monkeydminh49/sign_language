package com.ptit.sign.controller;

import com.ptit.sign.dto.MappingResponse;
import com.ptit.sign.entity.Label;
import com.ptit.sign.entity.Level;
import com.ptit.sign.service.LabelService;
import com.ptit.sign.service.LevelService;
import com.ptit.sign.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/api/v1")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private SubjectService subjectService;

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
        List<Object> levelList = new ArrayList<>();

        Map<String, Object> body = new HashMap<>();

        int total = 0;


        body.put("subjectId", subjectId);
        body.put("subjectName", subjectService.getSubjectById(Long.parseLong(subjectId)).getName());

        for (Level level : levels) {
            List<Label> labels = labelService.getLabelsByLevelIdsAndSubjectIds(level.getId().toString(), subjectId);
            if (labels != null && !labels.isEmpty()) {
                total += 1;
                Object levelObj = new Object() {
                    public final Long levelId = level.getId();
                    public final List<Label> listLabel = labels;
                };
                levelList.add(levelObj);

            }
        }

        body.put("total", total);
        body.put("listLevel", levelList);

        return MappingResponse.builder()
                .status("ok")
                .body(body)
                .message("Get labels successfully")
                .build();
    }

}
