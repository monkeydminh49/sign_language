package com.ptit.sign.dto;

import com.ptit.sign.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<UserRole> roles;
    JwtResponse token;
    private String dateOfBirth;
    private String address;
    private String phoneNumber;
    private String language;
    private String profileNumber;
    private String supportedBy;
    private String registerType;
    public String getProfileNumber() {
        return "PTIT" + String.format("%05d", getId());
    }

    public static class UserResponseBuilder {
        public UserResponseBuilder dateOfBirth(Date date) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.dateOfBirth = dateFormat.format(date);
            return this;
        }
    }
}
