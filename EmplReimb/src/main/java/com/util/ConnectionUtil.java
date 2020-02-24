package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionUtil {

public static Connection getConnection() {
		
	//environment variables to connect to database
//	System.out.println("Username is: " + System.getenv("uname"));
//	System.out.println("URL is: " + System.getenv("JDBC_URL"));
//	System.out.println("Password is : " + System.getenv("pword"));
	
	String url = "jdbc:postgresql://project1db.clcnvzawjmzr.us-east-2.rds.amazonaws.com:5432/postgres";
	
	//System.out.println(url);
	String uname = System.getenv("uname");
	//System.out.println(uname);
	String pword = System.getenv("pword");
	//System.out.println(pword);
	//String uname = "postgresP1";
	//String pword = "p1khaa0302";
	
	try {
		System.out.println("Trying to connect to the database...");
		return DriverManager.getConnection(url, uname, pword);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return null;
	}
	
}