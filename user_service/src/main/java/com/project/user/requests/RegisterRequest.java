package com.project.user.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 20, message = "Minimum 2 and Maximum 20 characters are allowed!!!")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email!")
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,}$", message = "Password must contain atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
    private String password;

    private String about;
}
