package com.epam.ta.database.dao.property;

import java.util.ResourceBundle;

public class SQLQueryReader {
	private static final String SQL_PROPERTY_FILE = "com." +
			"epam.ta.database.dao.property.sqlquery"; 
	
	private static final ResourceBundle bundle =
		ResourceBundle.getBundle(SQL_PROPERTY_FILE);
	
	private SQLQueryReader() {
	}
	
	public static String getSQlQuery(String key) {
		return bundle.getString(key);
	}
}
