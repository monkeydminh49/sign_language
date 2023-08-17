package com.ptit.sign.repository;

import com.ptit.sign.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findByName(String name);
}
