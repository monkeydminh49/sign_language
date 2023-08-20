package com.ptit.sign.repository;

import com.ptit.sign.entity.Label;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> ,
        JpaSpecificationExecutor<Label>
{
    Label findByLabelEnAndLabelVn(String labelEn, String LabelVn);

    default List<Label> findByLevelsAndSubjects (List <Long> levelList, List<Long> subjectList){
        Specification<Label> specification = (root, cq, cb) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();
            if (levelList != null && !levelList.isEmpty()) {
                predicates.add( root.get("level_id").in(levelList));
            }
            if (subjectList != null && !subjectList.isEmpty()) {
                predicates.add( root.get("subject_id").in(subjectList));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(specification);
    }
}
