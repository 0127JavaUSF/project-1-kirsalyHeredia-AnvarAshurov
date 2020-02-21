/*
 
 	Recap: MVC Design Pattern
 		We will be using Model-View-Controller pattern to establish separation of concerns.
 		
 		Model: It includes all of the basic / fundamental classes necessary for the project
 		View: This part is a concern for a Front-end developer
 		Controller: It performs all of the tasks that require Database modification / communication 
 
 */

package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.models.Employee;
import com.util.ConnectionUtil;

public class EmployeeController {

	// findByCredentails
	public static Employee findByCredentials(String username, String password) {
		
		// query for username
		try(Connection connection = ConnectionUtil.getConnection()) {
			
			String sql = "SELECT * FROM users WHERE username = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				// username found
				
				String hashedPass = rs.getString("password_digest");
				
				if(isPassword(password, hashedPass)) {
					// credentials verified
					
					return new 
				}
				
			} else {
				// username not found
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean isPassword(String plainText, String hashed) {
		
		return BCrypt.checkpw(plainText, hashed);
		
	}
	
	public static Employee extractEmployee(ResultSet rs) throws SQLException {
		
		/* 
		 
		private int userId;
		private String firstName;
		private String lastName;
		private String email;
		private String username;
		private int roleId;
		
		*/
		
		int userId = rs.getInt("user_id");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		String email = rs.getString("email");
		String username = rs.getString("username");
		int roleId = rs.getInt("role_id");
		
		return new Employee(userId, firstName, lastName, email, username, roleId);
		
	}
	
	// generateSessionToken
	
	// All Model Level Validations
	
	// Accesssors - not necessary
	
	// setPassword
	
	// ensureSessionToken
	
	// resetSessionToken
	
	// getSessionToken
	
	// setSessionToken
	
	// updatePassword
	
}