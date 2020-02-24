package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daos.EmployeeDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.EmployeeService;

public class EmployeeServlet extends HttpServlet {
	
	@Override
	public void init() throws ServletException {
		System.out.println("Servlet initializing");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		EmployeeService empService = new EmployeeService();
		
		System.out.println("I am doGet");
//		response.setContentType("application/json");
		
		ObjectMapper mapper = new ObjectMapper();
		
//		empService.getEmployee().get(0).getFirstName()
		String jsonInString = mapper.writeValueAsString(EmployeeDao.all().get(0));
		
		response.getWriter().write(jsonInString);
		
		
		// iterate
//		String thing = EmployeeController.all().toString();
		
//		response.getWriter().write(thing);
//		response.getWriter().write(allUsers.toString());		
		
	}
	
}
