package com.bridgelabz.employeepayroll.globalexceptionhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.employeepayroll.exceptionhandlers.UserException;
import com.bridgelabz.employeepayroll.exceptionhandlers.PayrollException;
import com.bridgelabz.employeepayroll.utility.Response;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {
		
	/**
	 * Handles the MethodArgumentNotValidException whenever thrown from any controller
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(new Response(400, ex.getMessage(), errors), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(PayrollException.class)
	public ResponseEntity<Response> handlerEmployeeException(PayrollException employeeException){
		Response response = new Response(403, employeeException.getMessage(), employeeException.getCause());
		ResponseEntity<Response> responseEntity= new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		log.info(responseEntity.toString());
		return responseEntity;
	}
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> customExceptionHandler(UserException exception) {
		return new ResponseEntity<>(new Response(403, exception.getMessage(), "Please provide valid credentials"),HttpStatus.BAD_REQUEST);
	}
	
	
}
