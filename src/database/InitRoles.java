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

			// drops admin role if it exists, run init database first to clear objects
			st.execute("REVOKE CREATE ON DATABASE \"final-project-db\" FROM admin;");
			System.out.println("Database CREATE permission revoked from admin");
			st.executeUpdate("DROP ROLE IF EXISTS admin;");
			System.out.println("admin role dropped");

			// create admin role
			st.executeUpdate("CREATE ROLE admin NOINHERIT;");
			st.execute("GRANT CREATE ON DATABASE \"final-project-db\" TO admin WITH GRANT OPTION;");
			st.execute("GRANT SELECT ON Login TO admin WITH GRANT OPTION;");
			st.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON Employee TO admin WITH GRANT OPTION;");
			st.execute("GRANT ALL ON Model TO admin WITH GRANT OPTION;");
			st.execute("GRANT ALL ON OrderInfo TO admin WITH GRANT OPTION;");
			st.execute("GRANT ALL ON Inventory TO admin WITH GRANT OPTION;");
			st.execute("GRANT ALL ON Customer TO admin WITH GRANT OPTION;");

			st.execute("GRANT SELECT ON employeeRevenue TO admin");
			st.execute("GRANT SELECT ON totalRevenue TO admin;");
			st.execute("GRANT SELECT ON customerModel TO admin;");
			st.execute("GRANT SELECT ON orderDetails TO admin;");
			System.out.println("Privileges granted to role admin!");

			// drops sales role if it exists, run init database first to clear objects
			st.executeUpdate("DROP ROLE IF EXISTS sales;");
			System.out.println("sales role dropped");

			// create sales user and granting privileges
			st.executeUpdate("CREATE ROLE sales NOINHERIT;");
			st.execute("GRANT SELECT, INSERT, DELETE, UPDATE ON Customer to sales;");
			st.execute("GRANT SELECT, INSERT, DELETE, UPDATE ON Inventory to sales;");
			st.execute("GRANT INSERT ON OrderInfo TO sales;");
			st.execute("GRANT SELECT ON Model TO sales;");
			// create views
			st.execute("GRANT SELECT ON totalRevenue TO sales;");
			st.execute("GRANT SELECT ON customerModel TO sales;");
			st.execute("GRANT SELECT ON orderDetails TO sales;");
			//st.execute("GRANT SELECT ON expenseReport TO sales;");

			System.out.println("Privileges granted to role salesperson");

			// drops engineering role if it exists, run init database first to clear objects
			st.executeUpdate("DROP ROLE IF EXISTS engineering;");
			System.out.println("engineering role dropped");

			// create engineering user and granting privileges
			st.executeUpdate("CREATE ROLE engineering NOINHERIT;");
			st.execute("GRANT SELECT, INSERT, DELETE, UPDATE ON Model TO engineering;");
			st.execute("GRANT SELECT, INSERT, DELETE, UPDATE ON Inventory TO engineering;");
			st.execute("GRANT SELECT (FirstName), SELECT (LastName), SELECT (JobType) ON Employee TO engineering;");
			// create views
			st.execute("GRANT SELECT ON totalRevenue TO engineering;");
			st.execute("GRANT SELECT ON customerModel TO engineering;");
			st.execute("GRANT SELECT ON orderDetails TO engineering;");
			//st.execute("GRANT SELECT ON expenseReport TO engineering;");

			System.out.println("Privileges granted to role engineering");

			// drops hr role if it exists, run init database first to clear objects
			st.executeUpdate("DROP ROLE IF EXISTS hr;");
			System.out.println("hr role dropped");

			// create hr user and grant privileges
			st.executeUpdate("CREATE ROLE hr NOINHERIT;");
			st.execute("GRANT SELECT, UPDATE ON Employee TO hr;");
			st.execute("GRANT SELECT ON employeeRevenue TO hr"); // grant view of employee and associated sales number
			System.out.println("Privileges granted to role hr");


		} catch (SQLException sqle) {
			System.out.println("SQL Error!");
			System.err.println(sqle);
		}

	}

}
