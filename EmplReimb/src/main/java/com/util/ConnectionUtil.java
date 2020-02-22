package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionUtil {

public static Connection getConnection() {
		
	//environment variables to connect to database
	String url = System.getenv("JDBC_URL");
	String uname = System.getenv("uname");
	String pword = System.getenv("pword");

	try {
		System.out.println("Trying to connect to the database...");
		return DriverManager.getConnection(url, uname, pword);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return null;
	}
	
}