package com.bridgelabz.employeepayroll.exceptionhandlers;

/**
 * @author Arjun
 * Exception class whose object is thrown when login credentials are invalid.
 */
@SuppressWarnings("serial")
public class UserException extends RuntimeException{
	public UserException(String message) {
		super(message);
	}
}
