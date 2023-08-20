package com.ptit.sign.repository;

import com.ptit.sign.entity.Label;
import com.ptit.sign.entity.Level;
import com.ptit.sign.entity.Subject;
import jakarta.persistence.criteria.Join;
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

    default List<Label> findByLevelsAndSubjects (List <String> levelList, List<String> subjectList){
        Specification<Label> specification = (root, cq, cb) -> {
            Join<Label, Level> levelJoin = root.join("level");
            Join<Label, Subject> subjectJoin = root.join("subject");

            ArrayList<Predicate> predicates = new ArrayList<>();
            if (levelList != null && !levelList.isEmpty()) {
                predicates.add( levelJoin.get("name").in(levelList));
            }
            if (subjectList != null && !subjectList.isEmpty()) {
                predicates.add( subjectJoin.get("name").in(subjectList));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(specification);
    }
}
