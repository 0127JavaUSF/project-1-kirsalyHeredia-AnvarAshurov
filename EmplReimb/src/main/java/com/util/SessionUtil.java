package com.util;

import com.daos.EmployeeDao;
import com.models.Employee;

/**
 * TODO: Identify how HTTP Session is related to SessionToken.
 * This could be redundant
 *
 */
public class SessionUtil {
	
	private static Employee currentUser;
	private static String sessionToken;
	
	// ensureLoggedIn method will REDIRECT to LOGIN page if USER is not logged in
	
	public static boolean isLoggedIn() {
		
		System.out.println(SessionUtil.getCurrentUser());
		
		if(SessionUtil.currentUser != null) {
			return true;
		} else {
			return false;
		}
//		return SessionUtil.getCurrentUser() == null;
	}
	
	public static void login(String username, String password) {
		
		Employee empl = EmployeeDao.findByCredentials(username, password);
		
		if(empl != null) {
			System.out.println("Logging in...");
			SessionUtil.setSessionToken(EmployeeDao.resetSessionToken(empl));
			SessionUtil.setCurrentUser(empl);
			System.out.println("Login complete");
		} else {
			System.out.println("Can't login");		
		}
		
	}
	
	public static void logout() {
		System.out.println("I am LOGOUT function");
		System.out.println("Curr user: " + SessionUtil.getCurrentUser());
		EmployeeDao.resetSessionToken(SessionUtil.getCurrentUser());
		
		SessionUtil.setCurrentUser(null);

		System.out.println("Curr user: " + SessionUtil.getCurrentUser());
		SessionUtil.setSessionToken(null);
		
		System.out.println("Logged out: success");	
		
	}
	
	public static Employee getCurrentUser() {
		return SessionUtil.currentUser;
	}
	
	public static void setCurrentUser(Employee empl) {
		SessionUtil.currentUser = empl;
	}
	
	public static String getSessionToken() {
		return SessionUtil.sessionToken;
	}
	
	public static void setSessionToken(String tkn) {
		SessionUtil.sessionToken = tkn;
	}
	
}
