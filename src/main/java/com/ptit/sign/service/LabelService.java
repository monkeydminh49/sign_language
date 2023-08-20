package com.ptit.sign.service;

import com.ptit.sign.entity.Label;
import com.ptit.sign.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    public List<Label> getLabelsByLevelsAndSubjects(String levels, String subjects) {
        List<String> levelList = (levels != null && !levels.isEmpty()) ?
                List.of(levels.split(",\\s*"))
                : null;
        List<String> subjectList = (subjects != null && !subjects.isEmpty()) ?
                List.of(subjects.split(",\\s*"))
                : null;
        return labelRepository.findByLevelsAndSubjects(levelList, subjectList);
    }
}
