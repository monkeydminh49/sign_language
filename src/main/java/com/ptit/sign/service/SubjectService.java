package com.ptit.sign.service;

import com.ptit.sign.entity.Subject;
import com.ptit.sign.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(long id) {
        return subjectRepository.findById(id).orElse(null);
    }
}
