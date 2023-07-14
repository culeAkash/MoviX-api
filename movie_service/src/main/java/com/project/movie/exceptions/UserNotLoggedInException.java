package com.project.movie.exceptions;

public class UserNotLoggedInException extends Exception {
	
	public UserNotLoggedInException() {
		super("Please login to access this URL");
	}

}
