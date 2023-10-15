package com.ptit.sign.dto;

import com.ptit.sign.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckVideoResponse implements Serializable {
    private boolean status;
    private String message;
    private String actionName;
    private String actionNameEn;
    private String videoPath;
    private float score;
}
