package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.models.Reimbursement;
import com.service.ReimbService;

public class ReimbServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int amount = Integer.parseInt(req.getParameter("amount"));
		String type = req.getParameter("type");
		String description = req.getParameter("description");
		String receipt = req.getParameter("receipt");
				
		//passing to service layer for validation before inputing to the db
		ReimbService.validateReimb(amount, type, description, receipt);
		
		resp.setStatus(201); // created
	}
	
	
}
