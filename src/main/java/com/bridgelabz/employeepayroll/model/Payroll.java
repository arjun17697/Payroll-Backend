package com.bridgelabz.employeepayroll.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity
@Table(name = "Employees")
public class Payroll {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String empName;
	private String gender;
	private String profileImage;
	private String[] departments;
	private Long salary;
	private String email;
	private LocalDateTime localDateTime = LocalDateTime.now();
	
}