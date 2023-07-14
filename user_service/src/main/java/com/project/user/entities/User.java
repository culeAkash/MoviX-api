package com.project.user.entities;

import java.util.ArrayList;
import java.util.List;

import com.project.user.payloads.ReviewDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class User {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@NotBlank(message = "Name must not be blank") // VALIDATION THAT NAME MUST NOT BE black, and if blank that message
	// must be printed
@Size(min = 2, max = 20, message = "Minimum 2 and Maximum 20 characters are allowed!!!")
	private String name;


	@Column(unique = true)
	@NotBlank(message = "Email must not be blank")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email!")
	private String email;

	@NotBlank(message = "Password must not be empty")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,}$", message = "Password must contain atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
	private String password;
	
	private Integer role;
	
	
	private String about;
	
	private String profileImage;

	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Integer getRole() {
		return role;
	}


	public void setRole(Integer role) {
		this.role = role;
	}

	public User(Long userId,
			@NotBlank(message = "Name must not be blank") @Size(min = 2, max = 20, message = "Minimum 2 and Maximum 20 characters are allowed!!!") String name,
			@NotBlank(message = "Email must not be blank") @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email!") String email,
			@NotBlank(message = "Password must not be empty") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,}$", message = "Password must contain atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ") String password,
			Integer role,String about,String profileImage) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.about = about;
		this.profileImage = profileImage;
	}


	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", role="
				+ role + ", about=" + about +  "]";
	}


	public String getAbout() {
		return about;
	}


	public void setAbout(String about) {
		this.about = about;
	}


	public String getProfileImage() {
		return profileImage;
	}


	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}


	 
		
	
	
}
