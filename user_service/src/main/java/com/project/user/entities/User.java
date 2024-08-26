package com.project.user.entities;

import java.util.ArrayList;
import java.util.List;

import com.project.user.enums.Active;
import com.project.user.enums.Role;
import com.project.user.payloads.ReviewDto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@NotBlank(message = "Name must not be blank") // VALIDATION THAT NAME MUST NOT BE black, and if blank that message
	// must be printed
	@Size(min = 2, max = 20, message = "Minimum 2 and Maximum 20 characters are allowed!!!")
	private String name;


	@Column(unique = true,updatable = false)
	@NotBlank(message = "Email must not be blank")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email!")
	private String email;

	@NotBlank(message = "Password must not be empty")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,}$", message = "Password must contain atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Enumerated(EnumType.STRING)
	private Active active;
	
	private String about;

	private String imageUrl;
	
	
}
