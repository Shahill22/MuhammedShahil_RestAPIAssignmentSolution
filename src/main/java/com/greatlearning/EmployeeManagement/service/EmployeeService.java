package com.greatlearning.EmployeeManagement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.greatlearning.EmployeeManagement.entity.Employee;

@Service
public interface EmployeeService {
	
	public List<Employee> findAll();
	
	public Employee findById(int theId);
	
	public void save(Employee theEmployee);

	public void deleteById(int theId);
	
	List<Employee> getEmployeeByName(String firstName);
	
	public List<Employee> getCustomSortedByName(String firstName, Direction direction);

}
