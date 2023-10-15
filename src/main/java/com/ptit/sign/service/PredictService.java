package com.ptit.sign.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptit.sign.component.UserInfoUserDetails;
import com.ptit.sign.dto.CheckVideoResponse;
import com.ptit.sign.entity.Label;
import com.ptit.sign.entity.UserScore;
import com.ptit.sign.repository.LabelRepository;
import com.ptit.sign.repository.UserScoreRepository;
import com.ptit.sign.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Slf4j
@Service
public class PredictService {

    @Value("${predict.videoToTexturl}")
    private String serverPredictUrl;

    @Value("${predict.videoFromTextUrl}")
    private String serverVideoUrl;

    @Value("${predict.api-key}")
    private String apiKey;

    @Autowired
    LabelRepository labelRepository;

    @Autowired
    UserScoreRepository userScoreRepository;

    public JsonNode predictVideoToText(MultipartFile multipartFile){
        JsonNode result = null;
        try {
            LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("video", multipartFile.getResource());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.add("APIKEY", apiKey);
            HttpEntity<MultiValueMap<String, Object>> requestEntity
                    = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate
                    .postForEntity(serverPredictUrl, requestEntity, String.class);
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readTree(response.getBody());
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return result;
    }

    public JsonNode getVideoFromText(String label){
        JsonNode result = null;
        try {
            String urlVideo = serverVideoUrl + "?action=" + label;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.add("APIKEY", apiKey);
            HttpEntity<String> entity = new HttpEntity<> (headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(urlVideo, HttpMethod.GET, entity, String.class);
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readTree(response.getBody());
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return result;
    }

    public CheckVideoResponse checkVideo(MultipartFile multipartFile, String label){
        CheckVideoResponse checkVideoResponse = new CheckVideoResponse();
        checkVideoResponse.setStatus(Constants.FAILED_STATUS);
        checkVideoResponse.setMessage(Constants.FAILED_MESSAGE);
        checkVideoResponse.setScore(0);
        checkVideoResponse.setActionName("");
        checkVideoResponse.setActionNameEn("");

        label = label.trim();
        String actionName = "";
        float actionScore = 0;
        JsonNode predictResponse = predictVideoToText(multipartFile);
        if (predictResponse.has("prediction") && predictResponse.has("status")){
            boolean status = predictResponse.get("status").asBoolean();
            if (status){
                JsonNode predictionNode = predictResponse.get("prediction");
                if (predictionNode != null) {
                    for (JsonNode predictNode : predictionNode) {
                        if (predictNode != null && predictNode.has("action_name") && predictNode.has("action_score")) {
                            String actionNameTmp = predictNode.get("action_name").asText("");
                            float actionScoreTmp = predictNode.get("action_score").floatValue();
                            if (actionScoreTmp > actionScore){
                                actionName = actionNameTmp.trim();
                                actionScore = actionScoreTmp;
                            }
                        }
                    }
                }
                if (actionScore > 0){
                    if (label.equalsIgnoreCase(actionName)) {
                        Label labelCheck = labelRepository.findByLabelEnEqualsIgnoreCaseOrLabelVnEqualsIgnoreCase(label, label);
                        if (labelCheck != null) {
                            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                            if (authentication != null) {
                                UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
                                UserScore userScore = userScoreRepository.findUserScoreByUserIdAndLabelId(userDetails.getId(), labelCheck.getId());
                                if (userScore != null) {
                                    if (userScore.getScore() > actionScore) {
                                        userScore.setScore(actionScore);
                                        userScore.setActionDate(new Date());
                                    }
                                } else {
                                    userScore = new UserScore();
                                    userScore.setUserId(userDetails.getId());
                                    userScore.setLabelId(labelCheck.getId());
                                    userScore.setScore(actionScore);
                                    userScore.setActionDate(new Date());
                                }
                                userScoreRepository.save(userScore);

                                checkVideoResponse.setScore(userScore.getScore());
                                checkVideoResponse.setStatus(Constants.SUCCESS_STATUS);
                                checkVideoResponse.setMessage(Constants.SUCCESS_MESSAGE);
                                checkVideoResponse.setActionName(labelCheck.getLabelVn());
                                checkVideoResponse.setActionNameEn(labelCheck.getLabelEn());
                            }
                        }
                    }
                }
            }
        }

        JsonNode videoResponse = getVideoFromText(label);
        if (videoResponse != null && videoResponse.has("status") && videoResponse.has("video_url")){
            boolean videoStatus = videoResponse.get("status").asBoolean();
            if (videoStatus){
                String videoPath = videoResponse.get("video_url").asText();
                checkVideoResponse.setVideoPath(videoPath);
            }
        }

        return  checkVideoResponse;
    }
}
