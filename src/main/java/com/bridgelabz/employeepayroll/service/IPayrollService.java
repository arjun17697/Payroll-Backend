package com.bridgelabz.employeepayroll.service;


import com.bridgelabz.employeepayroll.dto.UserDTO;
import com.bridgelabz.employeepayroll.dto.PayrollDTO;
import com.bridgelabz.employeepayroll.utility.Response;

public interface IPayrollService {
	public Response addEmployee(PayrollDTO employeePayrollDto);

	//public Response deleteByEmail(String email);

	//public Response getByEmail(String email);

	public Response getList();

	//public Response updateByEmail(PayrollDTO employeeDto);

	public Response updateById(PayrollDTO employeeDto);
	
	//public Response getSalaryAboveAverage();

	public Response getEmpById(Long id);
	
	public Response check(com.bridgelabz.employeepayroll.dto.UserDTO credentials);

	public Response addCredentials(UserDTO credentials);

	/**
	 *Deletes the employee with specified id
	 */
	Response deleteById(Long empId);
}
