package com.ptit.sign.service;

import com.ptit.sign.dto.ScoreWithSubjectResponse;
import com.ptit.sign.dto.UserScoreResponse;
import com.ptit.sign.entity.Label;
import com.ptit.sign.entity.User;
import com.ptit.sign.entity.UserLabelScore;
import com.ptit.sign.entity.UserScore;
import com.ptit.sign.repository.UserScoreRepository;
import com.ptit.sign.utils.Constants;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ScoreService {

    @Autowired
    private LabelService labelService;

    @Autowired
    private UserScoreRepository userScoreRepository;

    @Autowired
    EntityManager entityManager;
    @Autowired
    private User user;

    public ScoreWithSubjectResponse scoreWithSubject(String levelIds, String subjectIds){
        List<UserScoreResponse> scoreList = new ArrayList<>();
        ScoreWithSubjectResponse scoreResponse = new ScoreWithSubjectResponse();
        scoreResponse.setStatus(Constants.FAILED_STATUS);
        scoreResponse.setMessage(Constants.FAILED_MESSAGE);
        scoreResponse.setScoreList(scoreList);
        try {
            String labelIds = "";

            List<Label> labels = labelService.getLabelsByLevelIdsAndSubjectIds(levelIds, subjectIds);

            if (labels != null && !labels.isEmpty()) {
                for (Label label : labels) {
                    labelIds = StringUtils.join(labelIds, label.getId(), ",");
                }
                labelIds = labelIds.substring(0, labelIds.length() - 1);
            }
            List<UserLabelScore> scores = userScoreRepository.getUserLabelScore(labelIds);
            if (!scores.isEmpty()) {
                for (UserLabelScore userLabelScore : scores) {
                    UserScoreResponse userScore = new UserScoreResponse();
                    userScore.setScoreAverage(userLabelScore.getAverage());
                    userScore.setTotalLabel(userLabelScore.getTotalLabel());
                    userScore.setUserId(userLabelScore.getUserIdScore());
                    userScore.setUserName(userLabelScore.getUserNameScore());
                    userScore.setTotalScore(userLabelScore.getTotalScore());
                    scoreList.add(userScore);
                }
            }
            scoreResponse.setScoreList(scoreList);
            scoreResponse.setStatus(Constants.SUCCESS_STATUS);
            scoreResponse.setMessage(Constants.SUCCESS_MESSAGE);
        }catch (Exception e){
            log.error("scoreWithSubject error ", e);
        }
        return scoreResponse;
    }

    public UserScore getUserLatestScoreByLabelId(Long userId, Long labelId){
        return userScoreRepository.findUserLatestScoreByLabelId(userId, labelId);
    }

    public UserScore postUserScore(Long userId, Long labelId, float score) {
        ZoneId zid = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDate date = LocalDate.now(zid);
        UserScore userScore = new UserScore(userId, labelId, score, Date.valueOf(date));
        return userScoreRepository.save(userScore);
    }

    public List<UserScore> getTopUserScoresOfLabel(Long labelId, int top) {
        return userScoreRepository.findTopUserScoresOfLabel(labelId, top);

    }
}
