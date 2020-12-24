package com.bridgelabz.employeepayroll.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "LoginCredentials")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String email;
	private String password;

	@Override
	public boolean equals(Object obj) {
		User credentials = (User) obj;
		if (credentials.getEmail().equals(this.getEmail()) && credentials.getPassword().equals(this.getPassword()))
			return true;
		else
			return false;
	}
}