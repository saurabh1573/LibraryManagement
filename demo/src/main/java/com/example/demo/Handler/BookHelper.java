package com.example.demo.Handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.controller.BooknotavailableException;

@ControllerAdvice
public class BookHelper {

	@ExceptionHandler(BooknotavailableException.class)
	public ResponseEntity<String> handleBooknotavailableException(BooknotavailableException exception){
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.OK);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleusernotfoundException(BooknotavailableException exception){
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.OK);
	}
	
}
