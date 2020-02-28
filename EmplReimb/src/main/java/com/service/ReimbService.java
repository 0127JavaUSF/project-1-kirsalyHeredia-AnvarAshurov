package com.service;

import com.daos.ReimbDao;
import com.models.Reimbursement;

public class ReimbService {

	
	public static void validateReimb( Reimbursement reimb ) {
		
		//hardcoding 4 for danny until session works
		reimb.setAuthor(4);
		//all new reimb have a default status of pending(1)
		reimb.setStatusID(1);
		
		System.out.println(reimb);
		ReimbDao.createReimb(reimb);
		
	}
	
	public static void validateUpdate(String status) {
		
		//hardcode 2 for billy until session works
		//hardcode id 2
//		/ReimbDao.updateReimb(2, statusID, 2);
		
	}
	
}
