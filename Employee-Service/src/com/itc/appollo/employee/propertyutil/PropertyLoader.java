package com.itc.appollo.employee.propertyutil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class PropertyLoader {
	Logger logger = Logger.getLogger(PropertyLoader.class.getName());

	private static Properties prop = null;
	/**
	 * @param args
	 */
	private static PropertyLoader propertyloader = null;

	public static PropertyLoader getInstance(String propFilename) {
		if (propertyloader == null)
			propertyloader = new PropertyLoader(propFilename);
		return propertyloader;

	}
	public Set<Object> getPropertyAllkeys(){
		if(prop!=null){
			return prop.keySet();
		}
		return null;
	}

	private PropertyLoader(String propFilename) {

		if (propFilename != null && !propFilename.equalsIgnoreCase("")) {

			InputStream stream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(propFilename);

			prop = new Properties();

			try {
				prop.load(stream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else
			logger.warning("property file name is mandatory here");

	}

	public  String getValue(String strKey) {
		if (strKey != null && !strKey.equals(""))
			return prop.getProperty(strKey);
		else
			return null;

	}

	
}
