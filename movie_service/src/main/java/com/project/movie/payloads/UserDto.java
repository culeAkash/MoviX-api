package com.project.movie.payloads;

public class UserDto {
	
	private String name;
	private String email;

	private String password;
	
	private Integer role;

	

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

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDto(String name, String email, String password, Integer role) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserDto" +" [name=" + name + ", email=" + email + ", password=" + password
				+ ", role=" + role + "]";
	}
	
	
	
}
