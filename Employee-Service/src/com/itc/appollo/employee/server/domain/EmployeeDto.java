package com.itc.appollo.employee.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE")
public class EmployeeDto implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	protected String displayName;
	protected Date updated;
	protected String jobRole;
	protected String companyName;
	protected String projectDetails;
	protected String firstName;
	protected String lastName;
	protected String type;

	
	public int getId() {
		return id;
	}
	
	public EmployeeDto() {

	}

	public EmployeeDto(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String empType) {
		this.type = empType;
	}

	public String getProjectDetails() {
		return projectDetails;
	}

	public void setProjectDetails(String projectName) {
		this.projectDetails = projectName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
