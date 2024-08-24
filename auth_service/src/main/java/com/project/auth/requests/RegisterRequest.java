package com.project.auth.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String about;
}
