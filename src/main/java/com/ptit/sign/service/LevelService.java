package com.ptit.sign.service;

import com.ptit.sign.entity.Level;
import com.ptit.sign.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelService {

    @Autowired
    private LevelRepository levelRepository;
    public List<Level> getLevels() {
        return levelRepository.findAll();
    }
}
