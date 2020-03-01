/*
 
 	Recap: MVC Design Pattern
 		We will be using Model-View-Controller pattern to establish separation of concerns.
 		
 		Model: It includes all of the basic / fundamental classes necessary for the project
 		View: This part is a concern for a Front-end developer
 		Controller: It performs all of the tasks that require Database modification / communication 
 
 */

package com.daos;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;

import com.models.Employee;
import com.util.ConnectionUtil;

public class EmployeeDao {

	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
	private static final SecureRandom secureRandom = new SecureRandom();
	
	// findByCredentails
	public static Employee findByCredentials(String username, String password) {

		try(Connection connection = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM users WHERE username = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				String hashedPwd = rs.getString("password");
				if(EmployeeDao.isPassword(password, hashedPwd)) {
					return EmployeeDao.extractEmployee(rs);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Employee getEmployeeById(int userId) {
		
		try(Connection connection = ConnectionUtil.getConnection()) {
			
			String sql = "SELECT * FROM users WHERE user_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return EmployeeDao.extractEmployee(rs);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static boolean isPassword(String plainText, String hashedPwd) {
		
		return BCrypt.checkpw(plainText, hashedPwd);
		
	}
	
	public static Employee getCurrentUser(String sessionToken) {
		
		try(Connection connection = ConnectionUtil.getConnection()) {
			
			String sql = "SELECT * FROM users WHERE session_token = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, sessionToken);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return EmployeeDao.extractEmployee(rs);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
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
		String sessionToken = rs.getString("session_token");
		int roleId = rs.getInt("role_id");
		String password = rs.getString("password");
		
		return new Employee(userId, firstName, lastName, email, username, roleId, sessionToken);
		
	}
	
	/**
	 *  TODO: call this method after inserting a dummy record inside Employee table
	*/
	public static void updatePassword(String username, String password) {
		
		try(Connection connection = ConnectionUtil.getConnection()) {
		
			/**
			 *  TODO: Mitch talked about vulnerability of storing sensitive info in StringPool. Discuss.
			 */
			String passwordDigest = BCrypt.hashpw(password, BCrypt.gensalt());
			
			String sql = "UPDATE users SET COLUMN password = ? WHERE username = ?";
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
						
	public static String resetSessionToken(Employee empl) {
		
		String tkn = EmployeeDao.generateSessionToken();
		
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
	
	public static ArrayList<Employee> all() {
		
		ArrayList<Employee> allEmpl = new ArrayList<>();
		
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM users";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				Employee tpm = EmployeeDao.extractEmployee(rs);
				
				if(tpm != null) {
					allEmpl.add(tpm);
				}
			}
							
		} catch(SQLException e) {
			e.printStackTrace();
		}		
		return allEmpl;	
		
	}

}