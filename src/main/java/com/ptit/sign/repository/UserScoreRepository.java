package com.ptit.sign.repository;

import com.ptit.sign.entity.UserLabelScore;
import com.ptit.sign.entity.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long> ,
        JpaSpecificationExecutor<UserScore>
{
    UserScore findUserScoreByUserIdAndLabelId(long userId, long labelId);

    @Query(nativeQuery = true, value = "SELECT a.user_id_score as userIdScore" +
            ", a.user_name_score as userNameScore, a.total_label as totalLabel" +
            ", a.total_score as totalScore, a.average " +
            " from GET_USER_LABEL_SCORE(:labelIds) a")
    List<UserLabelScore> getUserLabelScore(String labelIds);
}
