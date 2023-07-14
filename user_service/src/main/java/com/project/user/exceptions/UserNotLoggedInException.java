package com.project.user.exceptions;

public class UserNotLoggedInException extends Exception {
	
	public UserNotLoggedInException() {
		super("Please login to access this URL");
	}

}
