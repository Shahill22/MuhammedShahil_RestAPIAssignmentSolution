package com.greatlearning.EmployeeManagement.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.greatlearning.EmployeeManagement.entity.Employee;
import com.greatlearning.EmployeeManagement.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;



	// add mapping for "/list"

	@RequestMapping("/list")
	public String listEmployees(Model theModel) {

	
		// get Books from db
		List<Employee> theEmployees = employeeService.findAll();
		

		// add to the spring model
		theModel.addAttribute("Employees", theEmployees);

		return "list-Employees";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Employee theEmployee = new Employee();

		theModel.addAttribute("Employee", theEmployee);

		return "Employee-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId,
			Model theModel) {

		// get the Book from the service
		Employee theEmployee = employeeService.findById(theId);


		// set Book as a model attribute to pre-populate the form
		theModel.addAttribute("Employee", theEmployee);

		// send over to our form
		return "Employee-form";			
	}


	@PostMapping("/save")
	public String saveBook(@RequestParam("id") int id,
			@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,@RequestParam("email") String email) {

		System.out.println(id);
		Employee theEmployee;
		if(id!=0)
		{
			theEmployee=employeeService.findById(id);
			theEmployee.setFirstname(firstName);
			theEmployee.setLastname(lastName);
			theEmployee.setEmail(email);
		}
		else
			theEmployee=new Employee(firstName, lastName, email);
		// save the Book
		employeeService.save(theEmployee);


		// use a redirect to prevent duplicate submissions
		return "redirect:/employee/list";

	}


	@RequestMapping("/delete/{employeeId}")
	public String delete(@RequestParam("employeeId") int theId) {

		// delete the Book
		employeeService.deleteById(theId);

		// redirect to /Books/list
		return "redirect:/employee/list";

	}
	
	@GetMapping("employee/{employeeId}")
	public Employee employee(@PathVariable int employeeId) {
		Employee theEmployee = employeeService.findById(employeeId);
		
		if(theEmployee == null) {
			throw new RuntimeException("Employee Id not found" + employeeId);
		}
		return theEmployee;
	}
	
	@GetMapping("employee/search/{firstName}")
	public List<Employee> getEmployeeByName(String firstName){
		return employeeService.getEmployeeByName(firstName);
	}
	
	@GetMapping("employee/sort/{firstName}")
	public List<Employee> getCustomSortedByName(String firstName, Direction direction){
		return employeeService.getCustomSortedByName(firstName, direction);
	}
	
	@RequestMapping(value = "/403")
	public ModelAndView accesssDenied(Principal user) {

		ModelAndView model = new ModelAndView();

		if (user != null) {
			model.addObject("msg", "Hi " + user.getName() 
			+ ", you do not have permission to access this page!");
		} else {
			model.addObject("msg", 
			"You do not have permission to access this page!");
		}

		model.setViewName("403");
		return model;

	}


	
}

