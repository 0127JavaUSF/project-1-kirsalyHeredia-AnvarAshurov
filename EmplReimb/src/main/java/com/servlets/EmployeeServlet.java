package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daos.EmployeeDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Employee;

public class EmployeeServlet extends HttpServlet {
	
	@Override
	public void init() throws ServletException {
		System.out.println("Servlet initializing");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
		resp.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		
		super.service(req, resp);
	}

	// show employee by id
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();		
		
		String[] params = request.getPathInfo().split("/");
		// EmplReimb/Employee/1
		
		System.out.println(request.getPathInfo());
		
		int userId = Integer.parseInt(params[2]);
		
		if(userId > 0) {
			Employee empl = EmployeeDao.getEmployeeById(userId);
			if(empl != null) {
				response.setStatus(201);
				response.getWriter().write(mapper.writeValueAsString(empl));
			} else {
				response.setStatus(404);
				response.getWriter().write(mapper.writeValueAsString("Employee with such id does not exist in our record."));
			}			
			
		} else {
			response.setStatus(422);
			mapper.writeValueAsString("Employee id must be greater than 0.");
		}
		
	}
	
}
