package com.project.auth.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    private String message;
    private Boolean isSuccessful;


}
