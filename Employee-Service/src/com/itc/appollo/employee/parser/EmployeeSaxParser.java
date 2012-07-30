package com.itc.appollo.employee.parser;

import java.io.IOException;
import java.io.StringBufferInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Content;
import org.apache.abdera.model.Content.Type;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.itc.appollo.employee.model.Employee;
import com.itc.appollo.employee.propertyutil.PropertyKeys;
import com.itc.appollo.employee.propertyutil.PropertyLoader;

public class EmployeeSaxParser extends DefaultHandler {
	
	private Content content=null;
	private String tempVal;
	private Employee emp;
	Boolean status=false;
	public EmployeeSaxParser(Content content) {
	this.content=content;
	   parseDocument();
	   
	}
	


public void parseDocument() {
		
		//get a factory
		
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			emp=new Employee();
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			if(content!=null && !content.getValue().equals(""))
			sp.parse(new StringBufferInputStream(content.getValue()), this);
			status=true;
			
		}catch(SAXException se) {
			se.printStackTrace();status=false;
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();status=false;
		}catch (IOException ie) {
			ie.printStackTrace();status=false;
		}
	}
	

@Override
public void startElement(String uri, String localName, String qName,
		Attributes attributes) throws SAXException {
	
}

@Override
public void characters(char[] ch, int start, int length)
		throws SAXException {

	tempVal = new String(ch,start,length);
}

@Override
public void endElement(String uri, String localName, String qName)
		throws SAXException {
	
	PropertyLoader loader=PropertyLoader.getInstance(PropertyKeys.STR_PROP_FILENAME);
	Set set =loader.getPropertyAllkeys();
	Iterator iter=set.iterator();
	while(iter.hasNext()){
	String str_key=(String) iter.next();
	String strempkey="";
	if(str_key.indexOf(PropertyKeys.STR_XML_ELEMENT_PROP_KEY)>-1){
		String str_act_key=loader.getValue(str_key);
		if(qName.equalsIgnoreCase(str_act_key)){
			 //String emp_key = "";//PropertyKeys.STR_EMPLOYEE__PROP_PREFIX_PROP.trim()+".".trim()+str_key.substring(str_key.indexOf(PropertyKeys.STR_XML_ELEMENT_PROP_KEY+"."));
			strempkey="EMPLOYEE" +"."+str_key.substring(str_key.indexOf(PropertyKeys.STR_XML_ELEMENT_PROP_KEY+"."));
		    System.out.println("Whats the the value i got here please check "+str_key.replaceAll(PropertyKeys.STR_XML_ELEMENT_PROP_KEY+".", "EMPLOYEE" +"."));
		    String str_act_emp_key=loader.getValue(str_key.replaceAll(PropertyKeys.STR_XML_ELEMENT_PROP_KEY+".", "EMPLOYEE" +"."));
		    System.out.println("I am getting "+str_act_emp_key+ "value i setting "+tempVal);
			try {
				emp
				.getClass()
				.getMethod(
						"set"
								+ str_act_emp_key.substring(0, 1)
										.toUpperCase()
								+ str_act_emp_key.substring(1), tempVal.getClass()).invoke(emp, tempVal);
				break;
			
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
	}
	
	



}
private Employee getEmp() {
	return emp;
}

private void setEmp(Employee emp) {
	this.emp = emp;
}
public EmployeeSaxReport runParseReport(){
	EmployeeSaxReport report=new EmployeeSaxReport();
	report.setPayLoad(emp);
	if(status)
	report.setStatus(EmployeeSaxReport.EMP_CONVERT_SUCCESS);
	return report;
}

public static void main(String[] args){
	Factory factory = new Abdera().getFactory();
	Content content=factory.newContent();
	content.setContentType(Type.XML);
	content.setValue("<employee><firstname>keshav</firstname><lastname>Gopalaiah</lastname><jobrole>Lead Consultant</jobrole><type>perm</type></employee>");
	EmployeeSaxParser spe = new EmployeeSaxParser(content);
	Employee emp =(Employee)spe.runParseReport().getPayLoad();
	System.out.println("emp "+emp.getFirstName());
	System.out.println(""+emp.getLastName());
}


}
