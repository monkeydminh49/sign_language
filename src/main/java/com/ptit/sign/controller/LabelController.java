package com.ptit.sign.controller;

import com.ptit.sign.component.UserInfoUserDetails;
import com.ptit.sign.dto.LabelWithScoreResponse;
import com.ptit.sign.dto.MappingResponse;
import com.ptit.sign.entity.Label;
import com.ptit.sign.entity.Level;
import com.ptit.sign.entity.User;
import com.ptit.sign.entity.UserScore;
import com.ptit.sign.service.LabelService;
import com.ptit.sign.service.LevelService;
import com.ptit.sign.service.ScoreService;
import com.ptit.sign.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
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
    private ScoreService scoreService;

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

    @GetMapping("/label-id/{label-id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public MappingResponse getLabelsById(
            @PathVariable("label-id") Long labelId
    ) {
        Label label = labelService.getLabelById(labelId);

        return MappingResponse.builder()
                .status("ok")
                .body(label)
                .message("Get labels successfully")
                .build();
    }

    @GetMapping("/list-labels-by-subjectId")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public MappingResponse getListLabelsBySubjectId(
            @RequestParam(name="subjectId") String subjectId,
            Authentication authentication
    ) {
        List<Level> levels = levelService.getLevels();
        List<Object> levelList = new ArrayList<>();

        Map<String, Object> body = new HashMap<>();

        int total = 0;
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        body.put("subjectId", subjectId);
        body.put("subjectName", subjectService.getSubjectById(Long.parseLong(subjectId)).getName());

        for (Level level : levels) {
            List<Label> labels = labelService.getLabelsByLevelIdsAndSubjectIds(level.getId().toString(), subjectId);
            if (labels != null && !labels.isEmpty()) {
                total += 1;
                List<LabelWithScoreResponse> labelWithScoreList = new ArrayList<>();
                for (Label label : labels){
                    UserScore userScore = scoreService.getUserLatestScoreByLabelId(user.getId(), label.getId());
                    Float latestUserScore = null;
                    if (userScore != null) {
                        latestUserScore = scoreService.getUserLatestScoreByLabelId(user.getId(), label.getId()).getScore();
                    }
                    LabelWithScoreResponse labelWithScore = new LabelWithScoreResponse(
                            label.getId(),
                            label.getLabelVn(),
                            label.getLabelEn(),
                            label.getSubjectId(),
                            label.getLevelId(),
                            latestUserScore
                    );
                    labelWithScoreList.add(labelWithScore);
                }
                Object levelObj = new Object() {
                    public final Long levelId = level.getId();
                    public final List<LabelWithScoreResponse> listLabel = labelWithScoreList;
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
