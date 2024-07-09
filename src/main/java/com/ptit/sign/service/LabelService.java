package com.ptit.sign.service;

import com.ptit.sign.entity.Label;
import com.ptit.sign.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    public List<Label> getLabelsByLevelIdsAndSubjectIds(String levelIds, String subjectIds) {
        List<Long> levelList = (levelIds != null && !levelIds.isEmpty()) ?
                Stream.of(levelIds.split(",\\s*"))
                        .map(Long::parseLong)
                        .toList()
                : null;
        List<Long> subjectList = (subjectIds != null && !subjectIds.isEmpty()) ?
                Stream.of(subjectIds.split(",\\s*"))
                        .map(Long::parseLong)
                        .toList()
                : null;

        return labelRepository.findByLevelsAndSubjects(levelList, subjectList);
    }

    public Label getLabelById(Long labelId) {
        return labelRepository.findById(labelId).orElseThrow(() -> new NotFoundException("Label with id = " + labelId + " not found!"));
    }
}
