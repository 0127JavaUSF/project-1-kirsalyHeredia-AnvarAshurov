package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daos.EmployeeDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Employee;
import com.util.LoginPojo;
import com.util.SessionUtil;

/**
 * Servlet implementation class LoginServlet
 */

public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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
	
	// get current user request
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("application/json");
		
		// this is currently logged in user's session token
		String currentSessionToken = SessionUtil.getSessionToken();
		Employee empl = EmployeeDao.getCurrentUser(currentSessionToken);
		ObjectMapper mapper = new ObjectMapper();
		
		if(empl != null) {
			String objToJson = mapper.writeValueAsString(empl);			
			response.setStatus(201);
			response.getWriter().write(objToJson);
		} else {
			// no one has logged in
			response.setStatus(404);
		}
		
	}
	
	// create a login session
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		
		// incoming JSON object contains only user-name and password
		LoginPojo loginDetails = mapper.readValue(request.getReader(), LoginPojo.class);
		
		if(SessionUtil.isLoggedIn()) {
			response.setStatus(422);
			response.getWriter().write(mapper.writeValueAsString("Someone is already logged in."));
			
		} else {
			
			SessionUtil.login(loginDetails.getUsername(), loginDetails.getPassword());	
			
			/**
			 * TODO: Is it bad to store current user as an object?
			 */
			
			if(SessionUtil.isLoggedIn()) {
				String jsonInString = mapper.writeValueAsString(SessionUtil.getCurrentUser());			
				response.getWriter().write(jsonInString);
				response.setStatus(201);
				
				//HttpSession session = request.getSession();
				//session.setAttribute("session_token", SessionUtil.getSessionToken());
				//response.setStatus(201);			
				//Cookie cookie = new Cookie("session_token", SessionUtil.getSessionToken());
				//cookie.setMaxAge(10*60);
				//response.addCookie(cookie);
				
			} else {
				response.setStatus(422);
				response.getWriter().write(mapper.writeValueAsString("Unable to login. This is our fault"));
			}
		}
	}
	
	// delete session
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");
		
		if(SessionUtil.isLoggedIn()) {
			SessionUtil.logout();
			if(SessionUtil.getCurrentUser() == null) {
				String jsonInString = mapper.writeValueAsString(SessionUtil.getCurrentUser());			
				response.getWriter().write(jsonInString);
				response.setStatus(201);
			} else {
				response.setStatus(422);
				response.getWriter().write(mapper.writeValueAsString("We were unable to log you out. This is our fault."));
			}			
		} else {
			response.setStatus(404);
			response.getWriter().write(mapper.writeValueAsString("There is no one logged in to log out."));
		}
		
	}
}