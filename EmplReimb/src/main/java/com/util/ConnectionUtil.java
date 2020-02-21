package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionUtil {

public static Connection getConnection() {
		
	//environment variables to connect to database
	String url = System.getenv("JDBC_URL");
	String uname = System.getenv("TEMP_UNAME");
	String pword = System.getenv("TEMP_PWORD");
	
	try {
		return DriverManager.getConnection(url, uname, pword);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return null;
	}
	
}
