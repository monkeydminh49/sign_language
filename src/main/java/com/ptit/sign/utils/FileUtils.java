package com.ptit.sign.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class FileUtils {
    public static JsonNode getFileResourceAsJson(String file){
        try(InputStream inputStream =Thread.currentThread().getContextClassLoader().getResourceAsStream(file)){
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputStream, JsonNode.class);
        }
        catch(Exception e){
            return  null;
        }
    }
}
