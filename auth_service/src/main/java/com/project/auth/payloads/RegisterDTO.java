package com.project.auth.payloads;

import lombok.Data;

@Data
public class RegisterDTO {
    private String userId;
    private String name;
    private String email;
    private String about;
}
