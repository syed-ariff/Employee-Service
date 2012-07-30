package com.itc.appollo.employee.parser;

import org.apache.abdera.model.Content;

import com.itc.appollo.employee.model.Employee;

public interface EmployeeParser {
	
	String getJobTitle();
	String getFirstname();
	String getLastName();
	String getEmpId();
	String getProjectDetails();
	String getLocation();
	Employee getEmployee();
	void populateContent(Employee emp);

}
