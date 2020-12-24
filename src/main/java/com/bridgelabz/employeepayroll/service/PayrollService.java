package com.bridgelabz.employeepayroll.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.employeepayroll.dto.UserDTO;
import com.bridgelabz.employeepayroll.dto.PayrollDTO;
import com.bridgelabz.employeepayroll.exceptionhandlers.UserException;
import com.bridgelabz.employeepayroll.exceptionhandlers.PayrollException;
import com.bridgelabz.employeepayroll.model.User;
import com.bridgelabz.employeepayroll.model.Payroll;
import com.bridgelabz.employeepayroll.repository.IUserRepo;
import com.bridgelabz.employeepayroll.repository.IPayrollRepo;
import com.bridgelabz.employeepayroll.utility.Response;
import com.bridgelabz.employeepayroll.utility.TokenHelper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PayrollService implements IPayrollService{
	
	@Autowired
	private IPayrollRepo empRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private TokenHelper tokenHelper;
	
	@Autowired
	private IUserRepo credentialsRepo;
	
	/**
	 *Method for adding the employee to the employee database
	 */
	@Override
	public Response addEmployee(PayrollDTO employeePayrollDto) {
		Optional<Payroll> employee = empRepository.findByEmpName(employeePayrollDto.getEmployeeName());
		if(employee.isPresent()) {
			throw new PayrollException(environment.getProperty("EMPLOYEE_ALREADY_PRESENT"));
		}
		Payroll emp = mapper.map(employeePayrollDto, Payroll.class);
		empRepository.save(emp);
		log.info(environment.getProperty("ADDITION_SUCCESSFULL"));
		Response response = new Response(200, environment.getProperty("ADDITION_SUCCESSFULL"),employeePayrollDto);
		return response;
	}

	/**
	 *Deletes the employee with specified id
	 */
	@Override
	public Response deleteById(Long id) {
		Payroll employeeCheck=empRepository.findById(id).orElse(null);
		if(employeeCheck == null) {
			throw new PayrollException(environment.getProperty("EMPLOYEE_FETCH_UNSUCCESSFULL"));
			}
		empRepository.deleteById(id);
		log.info(environment.getProperty("DELETION_SUCCESSFULL"));
		return new Response(200,environment.getProperty("DELETION_SUCCESSFULL"),null);
	}

	@Override
	public Response getEmpById(Long id) {
		Payroll employeePayrolls = empRepository.findById(id).orElseThrow(()
				-> new PayrollException(environment.getProperty("EMPLOYEE_FETCHed_UNSUCCESSFULL")));
		PayrollDTO employeeDto = mapper.map(employeePayrolls, PayrollDTO.class);
		log.info(environment.getProperty("SUCCESS"));
		return new Response(200, environment.getProperty("SUCCESS"), employeeDto);
		}


	/**
	 *Fetches all the employees.
	 */
	/*
	 * @Override public Response getList() {
	 * 
	 * List<Payroll> employeePayrolls = empRepository.getSorted().orElseThrow(() ->
	 * new PayrollException(environment.getProperty("LIST_LOAD_UNSUCCESSFULL")));
	 * log.info(environment.getProperty("LIST_LOADED_SUCCESSFULLY"));
	 * 
	 * //empRepository.findAll(sort) return new Response(200,
	 * environment.getProperty("LIST_LOADED_SUCCESSFULLY"), employeePayrolls); }
	 */
	
	@Override
	public Response getList() {
		List<Payroll> employeePayrolls=empRepository.findAll();
		System.out.println(employeePayrolls.toString());
		if(employeePayrolls.isEmpty()) {throw new PayrollException(environment.getProperty("LIST_LOAD_UNSUCCESSFULL"));}
		return new Response(200,environment.getProperty("LIST_LOADED_SUCCESSFULLY"), employeePayrolls);
	}

	/**
	 *Updates the employee with the specified email
	 */
	@Override
	public Response updateById(PayrollDTO employeeDto) {
		Payroll employeePayroll = empRepository.findById(employeeDto.getId()).orElseThrow(() 
				-> new PayrollException(environment.getProperty("EMPLOYEE_FETCH_UNSUCCESSFULL")));
		employeePayroll.setEmpName(employeeDto.getEmployeeName());
		employeePayroll.setSalary(employeeDto.getSalary());
		employeePayroll.setEmail(employeeDto.getEmail());
		empRepository.save(employeePayroll);
		log.info(environment.getProperty("UPDATE_SUCCESSFULL"));
		Response response = new Response(200, environment.getProperty("UPDATE_SUCCESSFULL"),employeeDto);
		return response;
	}

	public Response check(UserDTO credentials) {
		User credentials2 = credentialsRepo.findByEmail(credentials.getEmail()).orElseThrow(() -> new UserException(environment.getProperty("INVALID_CREDENTIALS")));
		log.info(credentials.toString());
		String token = tokenHelper.createJWT(credentials2.getId().toString(), environment.getProperty("token.issuer"), environment.getProperty("token.subject"), Long.parseLong(environment.getProperty("token.expirationTime"))); 
		UserDTO dto = mapper.map(credentials2, UserDTO.class);
		if(credentials.equals(dto))	
			return new Response(200, environment.getProperty("LOGIN_SUCCESSFULL"), token);
		else 
			return new Response(400, environment.getProperty("INVALID_CREDENTIALS"),null);
	}
	
	public Response addCredentials(UserDTO credentials) {
		credentialsRepo.findByEmail(credentials.getEmail()).ifPresent((emp) -> {throw new UserException(environment.getProperty("EMPLOYEE_ALREADY_PRESENT"));});
		User credentials3 = mapper.map(credentials, User.class);
		credentialsRepo.save(credentials3);
		log.info(environment.getProperty("SIGNUP_SUCCESSFULL"));
		return new Response(200, environment.getProperty("SIGNUP_SUCCESSFULL"), credentials);
	}



	
}