package com.ptit.sign.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ptit.sign.dto.MappingResponse;
import com.ptit.sign.service.PredictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class PredictController {

    @Autowired
    private PredictService predictService;

    @PostMapping("/predict")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public MappingResponse uploadFile(
            @RequestParam("file") MultipartFile multipartFile){
        JsonNode predictResponse = predictService.predictVideoToText(multipartFile);
        return MappingResponse.builder()
                .status("ok")
                .body(predictResponse)
                .message("")
                .build();
    }

    @GetMapping("/video")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public MappingResponse video(
            @Param("label") String label){
        JsonNode predictResponse = predictService.getVideoFromText(label);
        return MappingResponse.builder()
                .status("ok")
                .body(predictResponse)
                .message("")
                .build();
    }

    @PostMapping("/checkVideo")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public MappingResponse checkVideo(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("label") String label
    ){
        String actionName = "";
        float actionScore = 0;
        JsonNode predictResponse = predictService.predictVideoToText(multipartFile);
        if (predictResponse.has("prediction") && predictResponse.has("status")){
            boolean status = predictResponse.get("status").asBoolean();
            if (status){
                JsonNode predictionNode = predictResponse.get("prediction");
                if (predictionNode != null) {
                    for (JsonNode predictNode : predictionNode) {
                        if (predictNode != null && predictNode.has("action_name") && predictNode.has("action_score")) {
                            String actionNameTmp = predictNode.get("action_name").asText("");
                            float actionScoreTmp = predictNode.get("action_score").floatValue();
                            if (actionScoreTmp > actionScore){
                                actionName = actionNameTmp;
                                actionScore = actionScoreTmp;
                            }
                        }
                    }
                }
            }
        }
        return MappingResponse.builder()
                .status("ok")
                .body(predictResponse)
                .message("")
                .build();
    }
}
