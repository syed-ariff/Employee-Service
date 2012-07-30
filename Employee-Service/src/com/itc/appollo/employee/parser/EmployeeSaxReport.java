package com.itc.appollo.employee.parser;

public class EmployeeSaxReport {
	public static final String EMP_CONVERT_SUCCESS="success";
	public static final String EMP_CONVERT_FAILURE="failure";
	private String status=EMP_CONVERT_FAILURE;
	private Object payload;
	public void setPayLoad(Object obj){
		if(obj!=null){
			payload=obj;
		}
	}
	public Object getPayLoad(){
		if(payload!=null)
		return payload;
		else
			return null;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String str){
		if(str!=null && !str.equals("") && str.equalsIgnoreCase(EMP_CONVERT_SUCCESS)){
			status=EMP_CONVERT_SUCCESS;
			
		}
	}
	
	
	

}
