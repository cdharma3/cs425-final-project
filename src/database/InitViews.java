package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitViews {

	public static void main(String[] args) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", "mr_admin", "mr_password");
		Statement st = erpDB.createStatement();

		// drop and create totalRevenue view
		st.executeUpdate("DROP VIEW IF EXISTS totalRevenue;");
		System.out.println("TotalRevenue view dropped");

		String createTotalSales =
				"CREATE VIEW totalRevenue AS "
						+ "SELECT SUM(SaleValue) totalSales "
						+ "FROM OrderInfo;";
		st.execute(createTotalSales);
		System.out.println("TotalSales view created");

		// drop and create employeeRevenue view
		st.executeUpdate("DROP VIEW IF EXISTS employeeRevenue;");
		System.out.println("EmployeeRevenue view dropped");

		String createEmployeeRevenue =
				"CREATE VIEW employeeRevenue AS "
						+ "SELECT E_ID, SUM(SaleValue) salesPerEmployee "
						+ "FROM OrderInfo "
						+ "GROUP BY E_ID;";
		st.execute(createEmployeeRevenue);
		System.out.println("EmployeeRevenue view created");

		// drop and create orderDetails view
		st.executeUpdate("DROP VIEW IF EXISTS orderDetails;");
		System.out.println("OrderDetails view dropped");

		String createOrderDetails =
				"CREATE VIEW orderDetails AS "
						+ "SELECT OrderInfo.E_ID, Model.ModelName, OrderInfo.Quantity "
						+ "FROM OrderInfo NATURAL JOIN Model;";
		st.execute(createOrderDetails);
		System.out.println("OrderDetails view created");
	}

}
