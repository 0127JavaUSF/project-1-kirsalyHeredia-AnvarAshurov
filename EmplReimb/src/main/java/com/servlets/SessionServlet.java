package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Login;
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
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
		resp.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		
		super.service(req, resp);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// use this method to check who is currently logged in?
		
	}
	
	// Login
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("application/json");
		
		ObjectMapper om = new ObjectMapper();
		Login loginTmp = om.readValue(request.getReader(), Login.class);
		
		
		
		SessionUtil.login(loginTmp.getUsername(), loginTmp.getPassword());
		
		if(SessionUtil.isLoggedIn()) {
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(SessionUtil.getCurrentUser());			
			response.getWriter().write(jsonInString);
			response.setStatus(201);
			
			HttpSession session = request.getSession();
			session.setAttribute("session_token", SessionUtil.getSessionToken());
			response.setStatus(201);			
//			Cookie cookie = new Cookie("session_token", SessionUtil.getSessionToken());
//			cookie.setMaxAge(10*60);
//			response.addCookie(cookie);
		} else {
			response.setStatus(422);
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
		
		
//		response.getWriter().write("Logging out: " + SessionUtil.getCurrentUser().getFirstName());
		
		if(SessionUtil.isLoggedIn()) {
			System.out.println("Someone is logged in.");
			SessionUtil.logout();
			if(SessionUtil.getCurrentUser() == null) {
				ObjectMapper mapper = new ObjectMapper();
				String jsonInString = mapper.writeValueAsString(SessionUtil.getCurrentUser());			
				response.getWriter().write(jsonInString);
				response.setStatus(201);		
			} else {
				response.setStatus(422);
				response.getWriter().write("Somethin' wrong.");
			}
		} else {
			System.out.println("No one is logged in.");
		}
		
//		Cookie newCookie = new Cookie("session_token", "");
//		newCookie.setMaxAge(0);
//		response.addCookie(newCookie);	
		// reset session token inside database
		
	}
}