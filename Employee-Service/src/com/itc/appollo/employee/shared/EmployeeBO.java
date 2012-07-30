package com.itc.appollo.employee.shared;

import java.util.ArrayList;
import java.util.List;

import com.itc.appollo.employee.model.Employee;
import com.itc.appollo.employee.server.dao.EmployeeDao;
import com.itc.appollo.employee.server.domain.EmployeeDto;

public class EmployeeBO {

	public Long createEmployee(Employee emp) {
		EmployeeDao empDao = new EmployeeDao();

		return (long) empDao.createEmployee(createEmployeeDto(emp));

	}

	private EmployeeDto createEmployeeDto(Employee emp) {

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setCompanyName(emp.getCompanyName());
		employeeDto.setDisplayName(emp.getDisplayName());
		employeeDto.setFirstName(emp.getFirstName());
		employeeDto.setJobRole(emp.getJobRole());
		employeeDto.setLastName(emp.getLastName());
		employeeDto.setProjectDetails(emp.getProjectDetails());
		employeeDto.setType(emp.getType());
		employeeDto.setUpdated(emp.getUpdated());

		return employeeDto;
	}

	public List<Employee> retriveEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		EmployeeDao empDao = new EmployeeDao();		

		try {
			for (EmployeeDto emp : empDao.retrieveEmployees(50, 5)) {
				
				employees.add(createEmployeeVo(emp));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("inside BO--->"+employees.size());

		return employees;

	}

	private Employee createEmployeeVo(EmployeeDto emp) {
		Employee employee = new Employee();
		employee.setId(emp.getId());
		System.out.println("emp Id--->"+emp.getId());
		employee.setCompanyName(emp.getCompanyName());
		employee.setDisplayName(emp.getDisplayName());
		employee.setFirstName(emp.getFirstName());
		employee.setJobRole(emp.getJobRole());
		employee.setLastName(emp.getLastName());
		employee.setProjectDetails(emp.getProjectDetails());
		employee.setType(emp.getType());
		return employee;
	}

}
