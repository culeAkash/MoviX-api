package com.project.auth.payloads;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {
    private String token;
}
