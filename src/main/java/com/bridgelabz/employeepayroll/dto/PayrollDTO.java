package com.bridgelabz.employeepayroll.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PayrollDTO {
	
	private Long id;
	
	/**
	 * Name of the employee. Cannot be null, and should have atleast 3 characters
	 */
	@NotNull
	@Size(min = 3, message = "Name should have atleast 3 characters")
	private String employeeName;

	/**
	 * The salary of the employee. Can't be null.
	 */
	@NotNull
	private Long salary;

	/**
	 * The email of the employee. Can't be null.
	 */
	@NotNull
	private String email;

	@NotNull
	private String gender;

	@NotNull
	private String profileImage;

	@NotNull
	private String[] departments;

}
