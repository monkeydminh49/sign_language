package com.ptit.sign.controller;

import com.ptit.sign.dto.MappingResponse;
import com.ptit.sign.entity.Level;
import com.ptit.sign.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/v1")
public class LevelController {

    @Autowired
    private LevelService levelService;

    @GetMapping("/level")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public MappingResponse getLevels(){
        List<Level> levels =  levelService.getLevels();

        return MappingResponse.builder()
                .status("ok")
                .body(levels)
                .message("")
                .build();
    }

}
