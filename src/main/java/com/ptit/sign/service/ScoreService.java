package com.ptit.sign.service;

import com.ptit.sign.dto.ScoreWithSubjectResponse;
import com.ptit.sign.dto.UserScoreResponse;
import com.ptit.sign.entity.Label;
import com.ptit.sign.entity.UserLabelScore;
import com.ptit.sign.repository.UserScoreRepository;
import com.ptit.sign.utils.Constants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
