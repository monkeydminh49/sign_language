package com.ptit.sign.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ptit.sign.dto.MappingResponse;
import com.ptit.sign.service.PredictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
