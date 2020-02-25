package com.service;

import com.daos.ReimbDao;
import com.models.Reimbursement;

public class ReimbService {

	
	public static void validateReimb(int amount, String type, String description, String receipt ) {
		
		int typeID=0;
		
		switch(type) {
			case "Lodging": typeID = 1; break;
			case "Travel": typeID = 2; break;
			case "Food": typeID = 3; break;
			case "Other": typeID = 4; break;
		}
		
		//hardcoding 4 for danny until session works
		//all new reimb have a default status of pending(1)
		Reimbursement newReimb = new Reimbursement(0, amount, "", "", description, receipt, 4, 0, 1, typeID); 
		ReimbDao.createReimb(newReimb);
		
	}
	
	public static void validateUpdate(String status) {
		
		int statusID=1;
		
		switch(status) {
			case "Approve": statusID = 2; break;
			case "Deny": statusID = 3; break;
		}
		
		//hardcode 2 for billy until session works
		//hardcode id 2
		ReimbDao.updateReimb(2, statusID, 2);
		
	}
	
}
