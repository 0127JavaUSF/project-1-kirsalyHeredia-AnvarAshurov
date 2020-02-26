package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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

public class SessionServlet extends HttpServlet {
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// use this method to check who is currently logged in?
		
	}
	  
	// Login
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("application/json");
		System.out.println("Servlet username: " + request.getParameter("username"));
			
		Employee employee = EmployeeDao.findByCredentials(request.getParameter("username"), request.getParameter("password"));
		if(employee != null) {
			SessionUtil.login(employee);
			ObjectMapper objMapper = new ObjectMapper();
			response.setStatus(201);
			response.getWriter().write("Session token: " + SessionUtil.getSessionToken() + " <br/>");
			response.getWriter().write("First name: " + SessionUtil.getCurrentUser().getFirstName() + " <br/>");
			objMapper.writeValue(response.getWriter(), employee);
	
			Cookie cookie = new Cookie("session_token", SessionUtil.getSessionToken());
			response.addCookie(cookie);
			
		} else {
			response.setStatus(404);
		}
	}
	
	// delete session
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// findBy(sessionToken: tkn)
		// TODO: There is no need to extra Employee first. Since session_token is unique. We can just reset it right there and then.
//		String tmpCookie = null;
//		Cookie[] cookies = request.getCookies();
//		for(int i = 0; i < cookies.length; i++) {
//			if(cookies[i].getName().compareTo("session_token") == 0) {
//				tmpCookie = cookies[i].getValue();
//				cookies[i].setValue(null);
//			}
//		}
		// above is delete session token from front-end
		// below is from back-end
		
		// Employee employee = EmployeeDao.findByCredentials(tmpCookie);
		// TODO as of now, backend has currentUser that keeps track of currUser.
		// should I keep this?
		response.getWriter().write("Logging out: " + SessionUtil.getCurrentUser().getFirstName());
		SessionUtil.logout();
		
		Cookie newCookie = new Cookie("session_token", "");
		newCookie.setMaxAge(0);
		response.addCookie(newCookie);	
		response.setStatus(201);
		response.getWriter().write("Logged out successfully.");
		// reset session token inside database
		
	}
}