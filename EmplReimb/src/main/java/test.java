import java.sql.Connection;

import com.controller.EmployeeController;
import com.util.ConnectionUtil;

public class test {
	public static void main(String[] args) {
	
		Connection connection = ConnectionUtil.getConnection();
		System.out.println(connection);
		System.out.println(EmployeeController.all().toString());
			
	}
}