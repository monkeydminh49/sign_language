package com.ptit.demo.repository;

import com.ptit.demo.entity.Label;
import com.ptit.demo.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    Label findByLabelEnAndLabelVn(String labelEn, String LabelVn);
}
