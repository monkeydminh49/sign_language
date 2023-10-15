package com.ptit.sign.repository;

import com.ptit.sign.entity.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long> ,
        JpaSpecificationExecutor<UserScore>
{
    UserScore findUserScoreByUserIdAndLabelId(long userId, long labelId);
}
