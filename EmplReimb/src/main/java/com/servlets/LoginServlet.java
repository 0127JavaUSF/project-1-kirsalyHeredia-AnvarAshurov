package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daos.EmployeeDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Employee;
import com.util.SessionUtil;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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
	  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("application/json");
		System.out.println("Servlet username: " + request.getParameter("username"));
			
		Employee employee = EmployeeDao.findByCredentials(request.getParameter("username"), request.getParameter("password"));
		if(employee != null) {
			SessionUtil.login(employee);
			ObjectMapper objMapper = new ObjectMapper();
			objMapper.writeValue(response.getWriter(), employee);
		} else {
			response.setStatus(404);
		}
	}
}