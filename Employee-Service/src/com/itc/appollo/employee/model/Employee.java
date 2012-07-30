package com.itc.appollo.employee.model;

import java.util.Date;

import org.apache.abdera.model.AtomDate;

public class Employee {

	private int id;
	private String displayName;
	private String type;

	public String getDisplayName() {
		return displayName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	private Date updated;
	private String jobRole;
	private String companyName;
	private String projectDetails;
	private String firstName;
	private String lastName;

	public String getProjectDetails() {
		return projectDetails;
	}

	public void setProjectDetails(String projectDetails) {
		this.projectDetails = projectDetails;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = new AtomDate().getDate();
	}
	
	public static void main(String[] args){
		Employee emp=new Employee();
		emp.setUpdated(new AtomDate(new Date()).getDate());
		System.out.println("Here i am getting the dates "+emp.getUpdated());
	}
}
