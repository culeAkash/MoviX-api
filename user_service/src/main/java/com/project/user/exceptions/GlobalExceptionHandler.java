package com.project.user.exceptions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.project.user.payloads.ApiResponse;



@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	
	//Exception for hadnling HttpBadRequests
		@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
		public ResponseEntity<ApiResponse> handlerHttpRequestNotSupportedException(HttpRequestMethodNotSupportedException ex){
			ApiResponse apiResponse = new ApiResponse(ex.getMessage() + " for this URL", false);
			return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
		}


	@ExceptionHandler(GenericErrorResponse.class)
	public ResponseEntity<?> genericError(GenericErrorResponse exception) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", exception.getMessage());
		return new ResponseEntity<>(errors, exception.getHttpStatus());
	}



	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
		logger.warn("The resource requested doesn't exist");
		final String message = e.getMessage();
		final ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
		Map<String,String> errors = new HashMap<>();
		
		BindingResult bindingResult = e.getBindingResult();
		 bindingResult.getAllErrors().forEach(err->{
			 String fieldName = ((FieldError)err).getField();
			 String message = err.getDefaultMessage();
			 errors.put(fieldName, message);
		 });
		 
		 return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateColumnException.class)
	public ResponseEntity<ApiResponse> handleDuplicateCloumnException(DuplicateColumnException ex){
			return new ResponseEntity<>(new ApiResponse(ex.getMessage(),false),HttpStatus.NOT_ACCEPTABLE);
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
		
		logger.warn("No handler is found for this url in the service application");
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);
        
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }
	
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ApiResponse> handleAll(Exception ex, WebRequest request) {
		System.out.println(ex.getMessage());
	    	ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);
	    	return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}
