package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDatabase {
	public static void main(String[] args) {
		try {
			Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", "postgres",
					"123456");
			// prints if connection is established
			System.out.println("Connnection established...");
			// creates statement to run sql statements
			Statement st = db.createStatement();
			
			// creating login table
			String loginTable = "CREATE TABLE Login " + 
					"(U_ID varChar(50) NOT NULL, " + 
					"Privilege ENUM (‘admin’, ‘sales’, ‘HR’, ‘engineering’), " + 
					"LoginTime timestamp ‘HH24:MI’, " + 
					"LogoutTime timestamp ‘HH24:MI’, " + 
					"PRIMARY KEY (U_ID));";
			
			// execute login table creation statement
			st.executeUpdate(loginTable);
			
			// creating employee table
			String employeeTable = "CREATE TABLE Employee " +
					"(E_ID varChar(50) NOT NULL," +
					"FirstName varChar(50)," + 
					"LastName varChar(50)," + 
					"SSN varChar(9)," + 
					"Salary numeric(15, 2)," + 
					"HourlyVSalary ENUM (‘hourly’, ‘salaried’)," +
					"JobType ENUM (‘HR’, ‘Sale’, ‘Engineering’)," + 
					"U_ID varChar(50) NOT NULL," + 
					"PRIMARY KEY (E_ID)," + 
					"FOREIGN KEY (U_ID) REFERENCES Login(U_ID));";
					
			// execute employee table creation statement
			st.executeUpdate(employeeTable);
			
		} catch (SQLException sqle) {
			System.out.println("SQL Error!");
			System.err.println(sqle.toString());
		}
	}

}
