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
}
