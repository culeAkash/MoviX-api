package com.project.user.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	String resourceName;
	String fieldName;
	Long fieldValue;
	String fieldVal;

	public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
		super(String.format("%s is not found for %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public ResourceNotFoundException(String resourceName, String fieldName, String fieldVal) {
		super(String.format("%s is not found for %s : %s", resourceName, fieldName, fieldVal));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldVal = fieldVal;
	}

}
