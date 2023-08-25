package com.ptit.sign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String name;
    private String email;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private String language;
    private String supportedBy;
    private String registerType;
}
