package test;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.itc.appollo.employee.server.dao.EmployeeDao;
import com.itc.appollo.employee.server.domain.EmployeeDto;

public class EmployeeTestCase {

	private long startTimeMillis;
	@Before
	public void setUp() throws Exception {
		 startTimeMillis = System.currentTimeMillis();
		 DOMConfigurator.configure("log4j.xml");
	}

	

	@Test
	public void testCreateAccount() {
		EmployeeDto emp = new EmployeeDto();		
		emp.setFirstName("sdsd");
		emp.setLastName("sdreresd");
		emp.setJobRole("Consultant");
		emp.setProjectDetails("fdsf");
		emp.setType("permanent");
		EmployeeDao dao = new EmployeeDao();
		dao.createEmployee(emp);
	}

	

}
