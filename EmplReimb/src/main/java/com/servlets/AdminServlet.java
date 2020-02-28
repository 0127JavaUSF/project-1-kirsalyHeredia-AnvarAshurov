package com.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daos.ReimbDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Reimbursement;


public class AdminServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	ObjectMapper om = new ObjectMapper();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
		resp.addHeader("Access-Control-Allow-Methods", "GET POST PUT DELETE");
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		
		super.service(req, resp);
	}
	
   	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
   		
   		//admin: get all reimb
		List<Reimbursement> allUserReimbs = ReimbDao.getAllReimbs();
		
		if (allUserReimbs == null) {
			resp.setStatus(404);
			return;
		}
		
		resp.setContentType("application/json");
		om.writeValue(resp.getWriter(), allUserReimbs);	
	}
}
