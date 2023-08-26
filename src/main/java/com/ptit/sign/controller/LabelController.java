package com.ptit.sign.controller;

import com.ptit.sign.dto.MappingResponse;
import com.ptit.sign.entity.Label;
import com.ptit.sign.service.LabelService;
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

    @GetMapping("/labels-by-subjectId")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public MappingResponse getLabelsBySubjectId(
            @RequestParam(name="subjectId") String subjectId
    ) {
        List<Label> labels = labelService.getLabelsBySubjectId(Long.parseLong(subjectId));

        Map<String, List<Label>> map = new HashMap<>();
        for (Label label : labels) {
            String key = String.valueOf(label.getLevelId());
            if (map.containsKey(key)) {
                map.get(key).add(label);
            } else {
                map.put(key, new ArrayList<>());
                map.get(key).add(label);
            }
        }

        return MappingResponse.builder()
                .status("ok")
                .body(map)
                .message("Get labels successfully")
                .build();
    }

}
