package com.project.movie.exceptions;

public class ForbiddenRequestException extends Exception{


	
	public ForbiddenRequestException() {
		super("You don't have permission to access this URL");	
	}
	
	public ForbiddenRequestException(String role) {
		super(String.format("This url can only be accessed by %s",role));
	}
}
