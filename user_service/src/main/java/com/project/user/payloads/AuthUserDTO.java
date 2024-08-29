package com.project.user.payloads;

import com.project.user.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthUserDTO {
    private String userId;
    private String email;
    private String password;
    private Role role;
}
