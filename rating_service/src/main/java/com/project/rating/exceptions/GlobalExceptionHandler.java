package com.project.rating.exceptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.project.rating.payloads.ApiResponse;




@RestControllerAdvice
public class GlobalExceptionHandler {


	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


	//Exception for hadnling HttpBadRequests
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse> handlerHttpRequestNotSupportedException(HttpRequestMethodNotSupportedException ex){
		ApiResponse apiResponse = new ApiResponse(ex.getMessage() + " for this URL", false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
		final String message = e.getMessage();
		final ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
		logger.warn("The rating must be in the required range");
		Map<String,String> errors = new HashMap<>();

		BindingResult bindingResult = e.getBindingResult();
		bindingResult.getAllErrors().forEach(err->{
			String fieldName = ((FieldError)err).getField();
			String message = err.getDefaultMessage();
			errors.put(fieldName, message);
		});

		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<ApiResponse> ioExceptionHandler(IOException ex){
		return new ResponseEntity<ApiResponse>(new ApiResponse("File is not present",false),HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(ForbiddenRequestException.class)
	public ResponseEntity<ApiResponse> handlerForbiddenRequest(ForbiddenRequestException e) {
		final ApiResponse response = new ApiResponse(e.getMessage(), false);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.UNAUTHORIZED);
	}


	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse> handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
		String error =
				ex.getName() + " should be of type " + ex.getRequiredType().getName();

		ApiResponse apiResponse = new ApiResponse(error, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_ACCEPTABLE);
	}


	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity <ApiResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
		ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ApiResponse> handleAll(Exception ex) {
		ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}