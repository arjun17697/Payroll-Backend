package com.bridgelabz.employeepayroll.exceptionhandlers;

/**
 * @author Arjun
 * Exception class whose object is thrown when Database Operation is unsuccessfull
 */
@SuppressWarnings("serial")
public class PayrollException extends RuntimeException{
	public PayrollException(String message) {
		super(message);
	}
}
