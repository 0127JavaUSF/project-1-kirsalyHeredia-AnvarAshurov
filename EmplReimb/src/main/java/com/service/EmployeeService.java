package com.service;

import java.util.ArrayList;

import com.daos.EmployeeDao;
import com.models.Employee;

public class EmployeeService {
	
	// this is not used
	public ArrayList<Employee> getEmployee() {
		
		ArrayList<Employee> allEmpl = new ArrayList<>();
		allEmpl = EmployeeDao.all();
		return allEmpl;
		
	}
	
}
