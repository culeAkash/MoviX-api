package com.project.user.exceptions;

public class InvalidCredentialsException extends Exception {

	String res;

	public InvalidCredentialsException(String res) {
		super(String.format("%s",res));
		this.res = res;
	}

}
