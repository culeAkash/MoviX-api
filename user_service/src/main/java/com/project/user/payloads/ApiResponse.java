package com.project.user.payloads;

public class ApiResponse {


	private String message;
	private Boolean isSuccessful;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getIsSuccessful() {
		return isSuccessful;
	}
	public void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public ApiResponse(String message, Boolean isSuccessful) {
		this.message = message;
		this.isSuccessful = isSuccessful;
	}
	public ApiResponse() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ApiResponse [message=" + message + ", isSuccessful=" + isSuccessful + "]";
	}


}
