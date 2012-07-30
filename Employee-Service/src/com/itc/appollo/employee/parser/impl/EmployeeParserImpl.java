package com.itc.appollo.employee.parser.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Set;

import org.apache.abdera.model.Content;
import org.apache.abdera.protocol.server.RequestContext.Property;

import com.itc.appollo.employee.model.Employee;
import com.itc.appollo.employee.parser.EmployeeParser;
import com.itc.appollo.employee.parser.EmployeeSaxParser;
import com.itc.appollo.employee.parser.EmployeeSaxReport;
import com.itc.appollo.employee.propertyutil.PropertyKeys;
import com.itc.appollo.employee.propertyutil.PropertyLoader;

public class EmployeeParserImpl implements EmployeeParser {
	private Content content;
	private Employee emp;

	public EmployeeParserImpl(Content content) {
		this.content = content;
		emp = parseXml(content);
	}

	@Override
	public String getJobTitle() {
		if (emp != null)
			return emp.getJobRole();
		return null;
	}

	@Override
	public String getFirstname() {
		if (emp != null)
			return emp.getFirstName();
		return null;
	}

	@Override
	public String getLastName() {
		if (emp != null)
			return emp.getLastName();
		return null;
	}

	@Override
	public String getEmpId() {
		/*
		 * if(emp!=null) return emp.getId();
		 */
		return null;
	}

	@Override
	public String getProjectDetails() {
		if (emp != null)
			return emp.getProjectDetails();
		return null;
	}

	@Override
	public String getLocation() {
		if (emp != null)
			return emp.getFirstName();
		return null;
	}

	@Override
	public void populateContent(Employee emp) {
		if(content!=null){
			System.out.println("Here i am getting the content as this "+content);
			
			//now it is hardcoded later on i will remove this and have the implementation of JAXB
			PropertyLoader proploader=PropertyLoader.getInstance(PropertyKeys.STR_PROP_FILENAME);
			Set set=proploader.getPropertyAllkeys();
			System.out.println("Here i am getting the set as this "+set);
			Iterator iter=set.iterator();
			StringBuffer str_firstlement=new StringBuffer("<"+PropertyKeys.STR_EMPLOYEE_ELEMENT +">");
			StringBuffer str_lastlement=new StringBuffer("</"+PropertyKeys.STR_EMPLOYEE_ELEMENT +">");
			StringBuffer str_middlelement=new StringBuffer();
			while(iter.hasNext()){
				String str_key=(String)iter.next();
				//str_key=proploader.getValue(str_key);
				System.out.println("Here i am getting the values here please check it "+str_key);
				if(str_key.indexOf(PropertyKeys.STR_EMPLOYEE__PROP_PREFIX_PROP)>-1){
				try {
					str_key=proploader.getValue(str_key);
					str_middlelement.append("<"+str_key.toLowerCase().trim()+">"+emp
							.getClass()
							.getMethod(
									"get"
											+ str_key.substring(0, 1)
													.toUpperCase()
											+ str_key.substring(1), null).invoke(emp, null)+"</"+str_key.toLowerCase().trim()+">");
					
					System.out.println( "Here i got this middle element of XML "+str_middlelement.toString());
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			
			
			}
			str_firstlement.append(str_middlelement.toString()+str_lastlement); 
			content.setValue(str_firstlement.toString());
		}
		
		
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	private Employee parseXml(Content content) {
		if (content != null) {
			if (content.getValue() != null && !content.getValue().equals("")) {
				EmployeeSaxParser parser = new EmployeeSaxParser(content);
				EmployeeSaxReport report = parser.runParseReport();
				return (Employee) report.getPayLoad();

			}

		}
		return null;
	}

	@Override
	public Employee getEmployee() {
		// TODO Auto-generated method stub
		return getEmp();
	}

}
