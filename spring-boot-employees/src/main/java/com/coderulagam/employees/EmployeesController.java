package com.coderulagam.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderulagam.employees.model.Employee;

@RestController
public class EmployeesController {
	
	@Autowired
	private NameService nameService;
	
	@GetMapping
	@RequestMapping("/api/employees")
	public Employee getEmployeeDetails() {
		
		Employee emp = new Employee();
		
		// calling microservice here
		String name = nameService.callNameService();
		
		emp.setName(name);
		emp.setSalary(100);
		return emp;
	}

}
