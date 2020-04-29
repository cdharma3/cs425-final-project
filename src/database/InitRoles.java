package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitRoles {

	public static void main(String[] args) {
		// java class to create all the roles necessary for the database
		try {
			Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", "mr_admin",
					"mr_password");
			// prints if connection is established
			System.out.println("Connnection established...");
			// creates statement to run sql statements
			Statement st = db.createStatement();

			// create admin role
			st.executeUpdate("CREATE ROLE admin;");
			st.execute("GRANT SELECT ON Login TO admin;");
			st.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON Employee TO admin;");
			st.execute("GRANT ALL ON Model TO admin;");
			st.execute("GRANT ALL ON OrderInfo TO admin;");
			st.execute("GRANT ALL ON Inventory TO admin;");
			st.execute("GRANT ALL ON Customer TO admin;");
			System.out.println("Privileges granted to role admin!");

			// create sales user and granting privileges
			st.executeUpdate("CREATE ROLE sales;");
			st.execute("GRANT SELECT, UPDATE ON Customer to sales;");
			st.execute("GRANT INSERT ON OrderInfo TO sales;");
			// create views
			/*
			st.execute("GRANT SELECT ON totalRevenue TO sales;");
			st.execute("GRANT SELECT ON customerModel TO sales;");
			st.execute("GRANT SELECT ON orderDetails TO sales;");
			st.execute("GRANT SELECT ON expenseReport TO sales;");
			 */
			System.out.println("Privileges granted to role salesperson");

			// create engineering user and granting privileges
			st.executeUpdate("CREATE ROLE engineering;");
			st.execute("GRANT SELECT, UPDATE ON Model TO engineering;");
			st.execute("GRANT SELECT, UPDATE ON Inventory TO engineering;");
			st.execute("GRANT SELECT (FirstName), SELECT (LastName), SELECT (JobType) ON Employee TO engineering;");
			// create views
			/*
			st.execute("GRANT SELECT ON totalRevenue TO engineering;");
			st.execute("GRANT SELECT ON customerModel TO engineering;");
			st.execute("GRANT SELECT ON orderDetails TO engineering;");
			st.execute("GRANT SELECT ON expenseReport TO engineering;");
			 */
			System.out.println("Privileges granted to role engineering");

			// create hr user and grant privileges
			st.executeUpdate("CREATE ROLE hr;");
			st.execute("GRANT SELECT, UPDATE, INSERT, DELETE ON Employee TO hr;");
			System.out.println("Privileges granted to role hr");


		} catch (SQLException sqle) {
			System.out.println("SQL Error!");
			System.err.println(sqle);
		}

	}

}
