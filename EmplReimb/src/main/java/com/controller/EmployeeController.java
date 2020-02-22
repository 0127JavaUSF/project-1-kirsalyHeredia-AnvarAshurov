/*
 
 	Recap: MVC Design Pattern
 		We will be using Model-View-Controller pattern to establish separation of concerns.
 		
 		Model: It includes all of the basic / fundamental classes necessary for the project
 		View: This part is a concern for a Front-end developer
 		Controller: It performs all of the tasks that require Database modification / communication 
 
 */

package com.controller;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;

import com.models.Employee;
import com.util.ConnectionUtil;

public class EmployeeController {

	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
	private static final SecureRandom secureRandom = new SecureRandom();
	
	// findByCredentails
	public static Employee findByCredentials(String username, String password) {
		
		// query for username
		try(Connection connection = ConnectionUtil.getConnection()) {
			
			String sql = "SELECT * FROM users WHERE username = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			// user with username exists?
			if(rs.next()) {
				
				String hashedPass = rs.getString("password_digest");
				
				// password is correct?
				if(isPassword(password, hashedPass)) {
					
					return EmployeeController.extractEmployee(rs);
				
				} else {
					
					System.out.println("Password is incorrect. Try again");
					
				}
				
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
	
	/**
	 *  TODO: call this method after inserting a dummy record inside Employee table
	*/
	public static void updatePassword(String username, String password) {
		
		try(Connection connection = ConnectionUtil.getConnection()) {
		
			/**
			 *  TODO: Mitch talked about vulnarability of storing sensitive info in StringPool. Discuss.
			 */
			String passwordDigest = BCrypt.hashpw(password, BCrypt.gensalt());
			
			String sql = "UPDATE users SET COLUMN password_digest = ? WHERE username = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, passwordDigest);
			ps.setString(2,  username);
			
			if(ps.executeUpdate() != 0) {

				System.out.println(username + "'s password has been stored securily.");
			
			}
			
		} catch(SQLException e) {
			
			e.printStackTrace();
		
		}
		
	}
	
	// generateSessionToken
	public static String generateSessionToken() {
		
		/**
		 * Returns a Base64.Encoder that encodes using the URL and Filename safe type base64 encoding scheme.
		 */
		byte[] randomBytes = new byte[24];
		/**
		 * Parameters: bytes - the array to be filled in with random bytes.
		 */
		secureRandom.nextBytes(randomBytes);
		/**
		 * Encodes the specified byte array into a String using the Base64 encoding scheme.
		 * Parameter: the byte array to encode
		 */
		return base64Encoder.encodeToString(randomBytes);
		
	}
						
	public String resetSessionToken(Employee empl) {
		
		String tkn = EmployeeController.generateSessionToken();
		
		try(Connection connection = ConnectionUtil.getConnection()) {
		
			String sql = "UPDATE users SET session_token = ? WHERE username = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, tkn);
			ps.setString(2, empl.getUsername());
			
			if(ps.executeUpdate() != 0) {
				return tkn;	
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}