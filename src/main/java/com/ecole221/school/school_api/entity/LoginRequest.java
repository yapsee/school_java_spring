package com.ecole221.school.school_api.entity;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
