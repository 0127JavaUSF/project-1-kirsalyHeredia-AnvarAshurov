package com.service;

import com.daos.ReimbDao;
import com.models.Reimbursement;

public class ReimbService {

	
	public static void validateReimb( Reimbursement reimb ) {
		reimb.setStatusID(1);
		
		System.out.println(reimb);
		ReimbDao.createReimb(reimb);
	}
	
	public static void validateUpdate(Reimbursement reimb) {
		ReimbDao.updateReimb(reimb.getReimbID(), reimb.getStatusID(), reimb.getResolver());
	}
	
}
