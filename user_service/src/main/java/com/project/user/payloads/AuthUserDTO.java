package com.project.user.payloads;

import com.project.user.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUserDTO {
    private String userId;
    private String email;
    private String password;
    private Role role;
}
