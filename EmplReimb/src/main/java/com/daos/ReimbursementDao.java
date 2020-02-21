package com.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.models.Reimbursement;
import com.util.ConnectionUtil;

public class ReimbursementDao {

	//pull reimbursement information from the db----------------------------------------
	public static Reimbursement getReimb(int reimbID) {
		
		try(Connection connection = ConnectionUtil.getConnection()) { //get connection
			
			String sql = "SELECT * FROM reimbursement " +
					"WHERE reimbID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			// Set PreparedStatement parameters
			statement.setInt(1, reimbID);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				int balance = result.getInt("balance");
				return new Reimbursement();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	
	public static void createReimb(Reimbursement reimbursement) {
		try(Connection connection = ConnectionUtil.getConnection()) {
					
			String sql = "INSERT INTO reimbursement (amount, submitted, description, receipt, author, type_ID) " +
					" VALUES (?, ?, ?, ?, ?, ?) RETURNING *"; 
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, reimbursement.getAmount());
			statement.setString(2, reimbursement.getSubmitted());
			statement.setString(3, reimbursement.getDescription());
			statement.setString(4, reimbursement.getReceipt());
			statement.setInt(5, reimbursement.getAuthor());
			statement.setInt(6, reimbursement.getStatusID());
			
			statement.executeQuery();
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	
	// change the status of reimb, add the resolver and the time resolved-------------------
	public static Reimbursement updateReimb(int reimbID, int statusID, int resolver, String date) {
		try(Connection connection = ConnectionUtil.getConnection()) {
			
			String sql = "UPDATE reimbursement SET status_ID = ?," +
					"resolver = ?, resolved = ? " +
					" WHERE reimb_ID = ? RETURNING *"; 
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, statusID);
			statement.setInt(2, resolver);
			statement.setString(3, date);
			statement.setInt(4, reimbID);
			
			statement.executeQuery();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
