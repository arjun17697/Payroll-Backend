package com.bridgelabz.employeepayroll.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.employeepayroll.dto.UserDTO;
import com.bridgelabz.employeepayroll.dto.PayrollDTO;
import com.bridgelabz.employeepayroll.service.IPayrollService;
import com.bridgelabz.employeepayroll.utility.Response;
import com.bridgelabz.employeepayroll.utility.TokenHelper;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/employee")
@CrossOrigin
@Slf4j

@PropertySource("classpath:message.properties")
public class PayrollController {

	@Autowired
	Environment environment;

	@Autowired
	private IPayrollService payrollService;

	@Autowired
	private TokenHelper tokenHelper;

	/**
	 * Method for checking whether the credentials provided are valid or not
	 */
	@PostMapping("/login")
	public ResponseEntity<Response> checkCredentials(@Valid @RequestBody UserDTO credentials) {
		Response response = payrollService.check(credentials);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PostMapping("/logon")
	public ResponseEntity<Response> addCredentials(@Valid @RequestBody UserDTO credentials) {
		Response response = payrollService.addCredentials(credentials);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * @return returns the list of employees from the database
	 */
	@GetMapping
	public ResponseEntity<Response> getPayrollData(@RequestHeader(value = "Authorisation") String token) {
		Claims claims = tokenHelper.decodeJWT(token);
		log.info(claims.get("token", String.class));
		return new ResponseEntity<>(payrollService.getList(), HttpStatus.OK);
	}

	/**
	 * @param id
	 * @param token
	 * @return Emp corresponding to provided id
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Response> getEmpById(@PathVariable(name = "id") Long id,
			@RequestHeader(value = "Authorisation") String token) {
		Claims claims = tokenHelper.decodeJWT(token);
		log.info(claims.get("token", String.class));
		return new ResponseEntity<>(payrollService.getEmpById(id), HttpStatus.OK);
	}

	/**
	 * Posting the employeePayroll to database
	 */
	@PostMapping
	public ResponseEntity<Response> addEmployee(@Valid @RequestBody PayrollDTO payrollDto,
			@RequestHeader(value = "Authorisation") String token) {
		Claims claims = tokenHelper.decodeJWT(token);
		log.info(claims.get("token", String.class));
		return new ResponseEntity<>(payrollService.addEmployee(payrollDto), HttpStatus.OK);
	}

	/**
	 * Deleting the employee using the id.
	 */

	@GetMapping("delete/{id}")
	public ResponseEntity<Response> deleteEmployee(@PathVariable(name = "id") Long id,
			@RequestHeader(value = "Authorisation") String token) {
		Claims claims = tokenHelper.decodeJWT(token);
		log.info(claims.get("token", String.class));
		Response response = payrollService.deleteById(id);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * Updating the employee based on the id.
	 */
	@PutMapping
	public ResponseEntity<Response> updateById(@Valid @RequestBody PayrollDTO employeeDto,
			@RequestHeader(value = "Authorisation") String token) {
		Claims claims = tokenHelper.decodeJWT(token);
		log.info(claims.get("token", String.class));
		Response response = payrollService.updateById(employeeDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
