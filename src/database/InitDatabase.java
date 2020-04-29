package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDatabase {
	public static void main(String[] args) {
		/*
		 * WARNING, this file will drop all tables in the database
		 * and replace them with the new, fresh schema
		 * All data currently in the database will be lost
		 */
		try {
			Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", "mr_admin",
					"mr_password");
			// prints if connection is established
			System.out.println("Connnection established...");
			DatabaseMetaData pdbmd = db.getMetaData();
			// creates statement to run sql statements
			Statement st = db.createStatement();


			// dropping login table if it exists
			ResultSet tables = pdbmd.getTables(null, null, "login", null);
			if (tables.next()) {
				st.executeUpdate("DROP TABLE login CASCADE;");
				System.out.println("login table dropped");
			}

			// creating login table
			String loginTable =
					"CREATE TABLE Login "
							+ "(U_ID varChar(50) NOT NULL, "
							+ "Privilege varChar(15) CHECK (Privilege IN ('admin', 'sales', 'hr', 'engineering')), "
							+ "LoginTime timestamp, "
							+ "LogoutTime timestamp, "
							+ "PRIMARY KEY (U_ID));";

			// execute login table creation statement
			st.executeUpdate(loginTable);
			System.out.println("Login table created");


			// dropping employee table if it exists
			tables = pdbmd.getTables(null, null, "employee", null);
			if (tables.next()) {
				st.executeUpdate("DROP TABLE employee CASCADE;");
				System.out.println("Employee table dropped");
			}

			// creating employee table
			String employeeTable =
					"CREATE TABLE Employee "
							+ "(E_ID varChar(50) NOT NULL, "
							+ "FirstName varChar(50) NOT NULL, "
							+ "LastName varChar(50), "
							+ "SSN varChar(9), "
							+ "Salary numeric(15, 2), "
							+ "isHourly BOOLEAN, "
							+ "JobType varChar(15) CHECK (JobType IN ('admin', 'hr', 'sales', 'engineering')), "
							+ "U_ID varChar(50) NOT NULL, "
							+ "PRIMARY KEY (E_ID), "
							+ "FOREIGN KEY (U_ID) REFERENCES Login(U_ID));";

			// execute employee table creation statement
			st.executeUpdate(employeeTable);
			System.out.println("Employee table created");


			// dropping customer table if it exists
			tables = pdbmd.getTables(null, null, "customer", null);
			if (tables.next()) {
				st.executeUpdate("DROP TABLE CUSTOMER CASCADE;");
				System.out.println("Customer table dropped");
			}

			// creating customer table
			String customerTable =
					"CREATE TABLE Customer "
							+ "(FirstName varChar(50) NOT NULL, "
							+ "LastName varChar(50), "
							+ "C_ID varChar(50) NOT NULL, "
							+ "PRIMARY KEY (C_ID));";

			// execute customer table creation statement
			st.executeUpdate(customerTable);
			System.out.println("Customer table created");


			// dropping model table if it exists
			tables = pdbmd.getTables(null, null, "model", null);
			if (tables.next()) {
				st.executeUpdate("DROP TABLE model CASCADE;");
				System.out.println("Model table dropped");
			}

			// creating model table
			String modelTable =
					"CREATE TABLE Model "
							+ "(ModelName varChar(50), "
							+ "ModelNumber SERIAL, "
							+ "SalePrice numeric(15, 2), "
							+ "PRIMARY KEY (ModelNumber));";

			// execute model table creation statement
			st.executeUpdate(modelTable);
			System.out.println("Model table created");


			// dropping inventory table if it exists
			tables = pdbmd.getTables(null, null, "inventory", null);
			if (tables.next()) {
				st.executeUpdate("DROP TABLE inventory CASCADE;");
				System.out.println("Inventory table dropped");
			}

			// creating inventory table
			String inventoryTable =
					"CREATE TABLE Inventory "
							+ "(I_ID SERIAL, "
							+ "ModelNumber SERIAl, "
							+ "Cost numeric(15,2), "
							+ "Lead_time int, "
							+ "Category_type varChar(50), "
							+ "Quantity int CHECK (Quantity >= 0), "
							+ "PRIMARY KEY (I_ID), "
							+ "FOREIGN KEY (ModelNumber) REFERENCES Model(ModelNumber));";

			// execute inventory table creation statement
			st.executeUpdate(inventoryTable);
			System.out.println("Inventory table created");


			// dropping order table if it exists
			tables = pdbmd.getTables(null, null, "orderinfo", null);
			if (tables.next()) {
				st.executeUpdate("DROP TABLE orderinfo CASCADE;");
				System.out.println("Orderinfo table dropped");
			}

			// creating order table
			String orderInfoTable =
					"CREATE TABLE OrderInfo "
							+ "(OrderNumber SERIAL, "
							+ "C_ID varChar(50) NOT NULL, "
							+ "E_ID varChar(50) NOT NULL, "
							+ "ModelNumber SERIAL, "
							+ "SaleValue numeric(15,2), "
							+ "PRIMARY KEY (OrderNumber), "
							+ "FOREIGN KEY (E_ID) REFERENCES employee(E_ID), "
							+ "FOREIGN KEY (C_ID) REFERENCES customer(C_ID), "
							+ "FOREIGN KEY (ModelNumber) REFERENCES model(ModelNumber));";

			// execute order table creation statement
			st.executeUpdate(orderInfoTable);
			System.out.println("OrderInfo table created");

		} catch (SQLException sqle) {
			System.out.println("SQL Error!");
			System.err.println(sqle);
		}
	}

}