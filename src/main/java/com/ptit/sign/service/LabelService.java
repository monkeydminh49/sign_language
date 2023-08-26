package com.ptit.sign.service;

import com.ptit.sign.entity.Label;
import com.ptit.sign.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    public List<Label> getLabelsByLevelIdsAndSubjectIds(String levels, String subjects) {
        List<Long> levelList = (levels != null && !levels.isEmpty()) ?
                Stream.of(levels.split(",\\s*"))
                        .map(Long::parseLong)
                        .toList()
                : null;
        List<Long> subjectList = (subjects != null && !subjects.isEmpty()) ?
                Stream.of(subjects.split(",\\s*"))
                        .map(Long::parseLong)
                        .toList()
                : null;
        return labelRepository.findByLevelsAndSubjects(levelList, subjectList);
    }

    public List<Label> getLabelsBySubjectId(Long subjectId) {
        return labelRepository.findAllBySubjectId(subjectId);
    }
}
