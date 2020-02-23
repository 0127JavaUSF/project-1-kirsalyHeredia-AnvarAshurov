package com.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.models.Reimbursement;
import com.util.ConnectionUtil;

public class ReimbursementDao {
	
	//extract the db values into a java reimb object
	static Reimbursement extractReimb(ResultSet result) throws SQLException {
		int reimbID = result.getInt("reim_ID");
		int amount = result.getInt("amount");
		String submitted = result.getString("submitted");
		String resolved = result.getString("resolved");
		String description = result.getString("description");
		String receipt = result.getString("receipt");
		int author = result.getInt("author");
	 	int resolver = result.getInt("resolver");
		int statusID = result.getInt("status_ID");
		int typeID = result.getInt("type_ID");
		
		return new Reimbursement(reimbID, amount, submitted, resolved, 
				description, receipt, author, resolver, statusID, typeID);
	}
	
	
	//pull reimbursement information from the db----------------------------------------
	public static Reimbursement getReimb(int reimbID) {
		
		try(Connection connection = ConnectionUtil.getConnection()) { //get connection
			
			String sql = "SELECT * FROM reimbursement " +
					"WHERE reimbID = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			//PreparedStatement parameters
			statement.setInt(1, reimbID);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				int amount = result.getInt("amount");
				String submitted = result.getString("submitted");
				String resolved = result.getString("resolved");
				String description = result.getString("description");
				String receipt = result.getString("receipt");
				int author = result.getInt("author");
			 	int resolver = result.getInt("resolver");
				int statusID = result.getInt("status_ID");
				int typeID = result.getInt("type_ID");
				
				return new Reimbursement(reimbID, amount, submitted, resolved, 
						description, receipt, author, resolver, statusID, typeID);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	
	//create a reimbursement with the user input
	public static Reimbursement createReimb(Reimbursement reimbursement) {
		
		try(Connection connection = ConnectionUtil.getConnection()) {
					
			String sql = "INSERT INTO reimbursement (amount, description, receipt, author, status_ID, type_ID) " +
					" VALUES (?, ?, ?, ?) RETURNING *"; 
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, reimbursement.getAmount());
			statement.setString(2, reimbursement.getDescription());
			statement.setString(3, reimbursement.getReceipt());
			statement.setInt(4, reimbursement.getAuthor());
			statement.setInt(5,1); //all new reimb will have a status of pending(id=1)
			statement.setInt(6, reimbursement.getTypeID()); 
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return extractReimb(result); //updates the java reimb object with values from db
			}
				
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	// update the status of reimb, add the resolver and the time resolved from db-------------------
	public static Reimbursement updateReimb(int reimbID, int statusID, int resolver) {
		try(Connection connection = ConnectionUtil.getConnection()) {
			
			String sql = "UPDATE reimbursement SET status_ID = ?," +
					"resolver = ?" +
					" WHERE reimb_ID = ? RETURNING *"; 
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, statusID);
			statement.setInt(2, resolver);
			statement.setInt(3, reimbID);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return extractReimb(result); //updates the java reimb object with values from db
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//get all the reimbursements and store in a List
	public static List<Reimbursement> getAllReimbs() {
		List<Reimbursement> allReimbursements = new ArrayList<>();
		try(Connection connection = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM reimbursment RETURNING *";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ResultSet result = ps.executeQuery();
			
			while(result.next()) {
				allReimbursements.add(extractReimb(result));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return allReimbursements;
	}
	
	
	
	public static List<Reimbursement> userReimbs(){
		
		List<Reimbursement> userReimbs = new ArrayList<>();
		
		try (Connection connection = ConnectionUtil.getConnection()){
			
			String sql = "";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ResultSet result = ps.executeQuery();
			
			while(result.next()) {
				userReimbs.add(extractReimb(result));
			}
			
			
		} catch(SQLException e) {
			
		}
		
		return userReimbs;
	}
	
	
	
}
