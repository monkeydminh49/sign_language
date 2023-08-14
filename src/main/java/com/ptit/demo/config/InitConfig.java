package com.ptit.demo.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.ptit.demo.entity.Label;
import com.ptit.demo.entity.Level;
import com.ptit.demo.entity.Subject;
import com.ptit.demo.entity.User;
import com.ptit.demo.repository.LabelRepository;
import com.ptit.demo.repository.LevelRepository;
import com.ptit.demo.repository.SubjectRepository;
import com.ptit.demo.repository.UserRepository;
import com.ptit.demo.user.UserRole;
import com.ptit.demo.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InitConfig {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    LevelRepository levelRepository;

    @Autowired
    LabelRepository labelRepository;


    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            User admin = userRepository.findByEmail("admin@gmail.com").orElse(null);
            if (admin == null){
                admin = new User();
                admin.setName("admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setRoles(List.of(UserRole.ROLE_ADMIN));
                userRepository.save(admin);
            }

            JsonNode initData = FileUtils.getFileResourceAsJson("data/data.json");
            if (initData != null){
                for (JsonNode data : initData){
                    if (data.has("Chủ đề")) {
                        String subjectName = data.get("Chủ đề").asText();
                        Subject subject = subjectRepository.findByName(subjectName);
                        if (subject == null) {
                            subject = new Subject();
                            subject.setName(subjectName);
                            subject = subjectRepository.save(subject);
                        }
                    }
                    if (data.has("Cấp độ")){
                        String levelName = data.get("Cấp độ").asText();
                        Level level = levelRepository.findByName(levelName);
                        if (level == null) {
                            level = new Level();
                            level.setName(levelName);
                            level = levelRepository.save(level);
                        }
                    }
                }
                for (JsonNode data : initData){
                    if (data.has("Tiếng Việt") && data.has("Tiếng Anh")
                    && data.has("Chủ đề") && data.has("Cấp độ")){
                        String labelVn = data.get("Tiếng Việt").asText();
                        String labelEn = data.get("Tiếng Anh").asText();
                        Label label = labelRepository.findByLabelEnAndLabelVn(labelEn, labelVn);
                        if (label == null){
                            String subjectName = data.get("Chủ đề").asText();
                            Subject subject = subjectRepository.findByName(subjectName);
                            String levelName = data.get("Cấp độ").asText();
                            Level level = levelRepository.findByName(levelName);
                            if (subject != null && level != null) {
                                label = new Label();
                                label.setLabelEn(labelEn);
                                label.setLabelVn(labelVn);
                                label.setSubjectId(subject.getId());
                                label.setLevelId(level.getId());
                                labelRepository.save(label);
                            }
                        }
                    }
                }
            }

        };
    }
}
