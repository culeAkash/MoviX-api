package com.project.user.payloads;

import com.project.user.enums.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String about;
    private String password;
    private String imageUrl;
}
