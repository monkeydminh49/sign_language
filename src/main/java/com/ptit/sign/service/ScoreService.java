package com.ptit.sign.service;

import com.ptit.sign.dto.ScoreWithSubjectResponse;
import com.ptit.sign.dto.UserScore;
import com.ptit.sign.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ScoreService {

    public ScoreWithSubjectResponse scoreWithSubject(String levelIds, String subjectIds){
        List<UserScore> scoreList = new ArrayList<>();
        ScoreWithSubjectResponse scoreResponse = new ScoreWithSubjectResponse();
        scoreResponse.setStatus(Constants.FAILED_STATUS);
        scoreResponse.setMessage(Constants.FAILED_MESSAGE);
        scoreResponse.setScoreList(scoreList);

        return scoreResponse;
    }
}
