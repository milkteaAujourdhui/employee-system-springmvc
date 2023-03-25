package com.kskelvin.springboot.springdemo.controller;

import com.kskelvin.springboot.springdemo.entity.Employee;
import com.kskelvin.springboot.springdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// add mapping for "/list"

	@GetMapping("/list")
	public String listEmployees(Model model) {

		List<Employee> employees = employeeService.findAll();

		// add to the spring model
		model.addAttribute("employees", employees);

		return "employees/list-employees";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {

		Employee employee = new Employee();
		model.addAttribute("employee", employee);

		return "employees/employee-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int id, Model model) {

		Employee employee = employeeService.findById(id);
		model.addAttribute("employee", employee);

		return "employees/employee-form";
	}

	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee employee){
		employeeService.save(employee);
		return "redirect:/employees/list";
	}

	@GetMapping("/delete")
	public String delete(@ModelAttribute("employeeId") int id){
		employeeService.delete(id);
		return "redirect:/employees/list";
	}
}









