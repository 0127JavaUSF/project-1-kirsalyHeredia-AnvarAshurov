package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Reimbursement;
import com.service.ReimbService;

public class ReimbServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ObjectMapper om = new ObjectMapper();
		Reimbursement reimb = om.readValue(req.getReader(), Reimbursement.class);
						
		System.out.println(reimb); //check
		
		//passing to service layer for validation before inputing to the db
		ReimbService.validateReimb(reimb);
		resp.setStatus(201); // created

	}
	
	
}
