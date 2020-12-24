package com.bridgelabz.employeepayroll.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.employeepayroll.model.Payroll;

@Repository
public interface IPayrollRepo extends JpaRepository<Payroll, Long>{
	
	/**
	 * fetches the employee with the specified email
	 */
	public Optional<Payroll> findById(Long id);
	public Optional<Payroll> findByEmpName(String employeeName);

	/**
	 * @return returns the employees with salary greater than average salary
	 */
	@Query(value = "Select * from employees where salary > (select avg(salary) from employees)",nativeQuery = true)
	Payroll[] getAboveAverage();

	/**
	 * @return returns the employees sorted by id.
	 */
	@Query(value = "Select * from employees",nativeQuery = true)
	Optional<List<Payroll>> getSorted();

	void deleteById(Long id);
	
}
