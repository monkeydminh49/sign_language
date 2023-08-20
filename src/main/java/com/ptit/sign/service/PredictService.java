package com.ptit.sign.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class PredictService {

    @Value("${predict.videoToTexturl}")
    private String serverPredictUrl;

    @Value("${predict.videoFromTextUrl}")
    private String serverVideoUrl;

    @Value("${predict.api-key}")
    private String apiKey;

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
}
