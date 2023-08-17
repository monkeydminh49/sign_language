package com.ptit.sign.repository;

import com.ptit.sign.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    Label findByLabelEnAndLabelVn(String labelEn, String LabelVn);
}
