package com.project.auth.payloads;

import com.project.auth.enums.Role;
import lombok.Data;

@Data
public class UserDTO {
    private String userId;
    private String name;
    private String email;
    private String password;
    private Role role;
}
