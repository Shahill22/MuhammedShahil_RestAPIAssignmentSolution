package com.greatlearning.EmployeeManagement.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.greatlearning.EmployeeManagement.entity.Employee;
import com.greatlearning.EmployeeManagement.repository.EmployeeRepository;
import com.greatlearning.EmployeeManagement.repository.UserRepository;
import com.greatlearning.EmployeeManagement.service.EmployeeService;

@Repository
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Transactional
	public List<Employee> findAll() {

		List<Employee> students = employeeRepository.findAll();

		return students;
	}

	@Transactional
	public Employee findById(int id) {

		Employee employee = new Employee();

		employee = employeeRepository.findById(id).get();

		return employee;
	}

	@Transactional
	public void save(Employee theEmployee) {

		employeeRepository.save(theEmployee);

	}

	@Transactional
	public void deleteById(int id) {

		employeeRepository.deleteById(id);

	}
	
	public List<Employee> getEmployeeByName(String firstName){
		Employee employeeWithName = new Employee();
		employeeWithName.setFirstname("firstName");
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.exact())
				.withIgnorePaths("id", "lastName", "email");
		Example<Employee> example = Example.of(employeeWithName, exampleMatcher);
		return employeeRepository.findAll(example);
	}
	
	public List<Employee> getCustomSortedByName(String firstName, Direction direction){
		Employee employeeWithName = new Employee();
		employeeWithName.setFirstname("firstName");
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.exact())
				.withIgnorePaths("id", "lastName", "email");
		Example<Employee> example = Example.of(employeeWithName, exampleMatcher);
		return employeeRepository.findAll(example, Sort.by(direction));
	}
	
	

}