package com.itc.appollo.employee.server;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.AtomDate;
import org.apache.abdera.model.Content;
import org.apache.abdera.model.Person;
import org.apache.abdera.model.Text;
import org.apache.abdera.model.Text.Type;
import org.apache.abdera.protocol.server.RequestContext;
import org.apache.abdera.protocol.server.context.ResponseContextException;
import org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter;

import com.itc.appollo.employee.model.Employee;
import com.itc.appollo.employee.parser.EmployeeParser;
import com.itc.appollo.employee.parser.impl.EmployeeParserImpl;
import com.itc.appollo.employee.shared.EmployeeBO;

public class EmployeeCollectionAdapter extends
		AbstractEntityCollectionAdapter<Employee> {
	private static final String ID_PREFIX = "tag:itc.com,2012:employee:entry:";

	private AtomicInteger nextId = new AtomicInteger(1000);
	private Map<Integer, Employee> employees = new HashMap<Integer, Employee>();
	private Factory factory = new Abdera().getFactory();

	private EmployeeBO empBO = new EmployeeBO();

	public String getId(RequestContext request) {
		return "tag:itc.com,2012:employee:feed";
	}

	/**
	 * The title of our collection.
	 */
	public String getTitle(RequestContext request) {
		return "itc Employee Database";
	}

	/**
	 * The author of this collection.
	 */
	public String getAuthor(RequestContext request) {
		return "itc Industries";
	}

	public Iterable<Employee> getEntries(RequestContext request) {
//		return employeeVos.values();
		
		return empBO.retriveEmployees();
	}

	public Employee getEntry(String resourceName, RequestContext request)
			throws ResponseContextException {
		Integer id = getIdFromResourceName(resourceName);
		System.out.println("The Id element i got here is " + id);
		System.out.println("The Id element i got here is "
				+ employees.get(id));
		return employees.get(id);
	}

	private Integer getIdFromResourceName(String resourceName)
			throws ResponseContextException {
		int idx = resourceName.indexOf("-");
		if (idx == -1) {
			throw new ResponseContextException(404);
		}
		return new Integer(resourceName.substring(0, idx));
	}

	public String getName(Employee entry) {
		// System.out.println("it returns this now please check it "+entry.getId()
		// + "-" + entry.getDisplayName().replaceAll(" ", "_"));
		return entry.getId() + "-" + entry.getFirstName()
				+ entry.getLastName().replaceAll(" ", "_");
	}

	public String getId(Employee entry) {
		return ID_PREFIX + entry.getId();
	}

	public String getTitle(Employee entry) {
		return entry.getDisplayName();
	}

	public Date getUpdated(Employee entry) {
		return entry.getUpdated();
	}

	public List<Person> getAuthors(Employee entry, RequestContext request)
			throws ResponseContextException {
		Person author = request.getAbdera().getFactory().newAuthor();
		author.setName("itc Industries");
		return Arrays.asList(author);
	}

	public Object getContent(Employee entry, RequestContext request) {
		Content content = factory.newContent(Content.Type.XML);
		EmployeeParser parser = new EmployeeParserImpl(content);
		parser.populateContent(entry);
		return content;
	}

	@Override
	public Text getSummary(Employee entry, RequestContext request)
			throws ResponseContextException {
		Text text = factory.newSummary();
		text.setTextType(Type.HTML);
		text.setText("<div>" + entry.getDisplayName() + ","
				+ entry.getJobRole() + entry.getCompanyName() + ",</div>");
		return text;
		// text.set

	}

	public Employee postEntry(String title, IRI id, String summary,
			Date updated, List<Person> authors, Content content,
			RequestContext request) throws ResponseContextException {

		Employee employee = populateEmployee(content);		
		empBO.createEmployee(employee);
		employees.put(employee.getId(), employee);
		System.out.println("Employees are dcsc" + employees);

		return employee;
	}

	private Employee populateEmployee(Content content) {

		EmployeeParser parser = new EmployeeParserImpl(content);
		Employee employee = parser.getEmployee();
		employee.setId(nextId.getAndIncrement());
		employee.setUpdated(new AtomDate().getDate());
		return employee;
	}

	public void putEntry(Employee employee, String title, Date updated,
			List<Person> authors, String summary, Content content,
			RequestContext request) throws ResponseContextException {
		EmployeeParser parser = new EmployeeParserImpl(content);
		employee.setDisplayName(parser.getFirstname() + " "
				+ parser.getLastName());
		employee.setCompanyName(parser.getJobTitle());
		employee.setFirstName(parser.getFirstname());
		employee.setLastName(parser.getLastName());
		employee.setJobRole(parser.getJobTitle());
		employee.setProjectDetails(parser.getProjectDetails());
		employee.setUpdated(updated);

	}

	public void deleteEntry(String resourceName, RequestContext request)
			throws ResponseContextException {
		Integer id = getIdFromResourceName(resourceName);
		employees.remove(id);
	}

}
