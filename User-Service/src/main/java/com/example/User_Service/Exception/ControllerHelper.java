package com.example.User_Service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerHelper {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> user(UserNotFoundException ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.OK);
	}
}
