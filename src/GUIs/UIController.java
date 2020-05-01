package GUIs;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UIController {
	private static final int iterations = 20*1000;
	private static final int saltLen = 32;
	private static final int desiredKeyLen = 256;
	private static String databaseUsername = "";
	private static String databasePassword = "";
	private static UUID currentSessionID;

	/**
	 * Hashes password with given plaintext password and given salt
	 * @param password plaintext password to be hashed and salted
	 * @param salt randomly generated 32bit salt length to generate unique hashes
	 * @return returns hashed password as encoded base64 string
	 * @throws IllegalArgumentException when plaintext password is null or empty
	 */
	private static String hash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException("Empty passwords are not supported.");
		}
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey key = f.generateSecret(new PBEKeySpec(
				password.toCharArray(), salt, iterations, desiredKeyLen));
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	/**
	 * Helper function to bundle salt and hashed password together to store in database
	 * @param password plaintext password to be hashed and salted
	 * @returns salt and hashed password as encoded base64 string separated by $
	 */
	public static String getSaltedHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
		byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
		// store the salt with the password
		return Base64.getEncoder().encodeToString(salt) + "$" + hash(password, salt);
	}

	/**
	 * Checks whether given plaintext password corresponds to a stored salted hash of the password.
	 * @param password plaintext password to be checked against stored hash
	 * @param stored salted hash to be checked again in this format 'salt$hash'
	 * @returns returns true if plaintext password equals the stored password
	 */
	public static boolean check(String password, String stored) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String[] saltAndHash = stored.split("\\$");
		if (saltAndHash.length != 2) {
			throw new IllegalStateException(
					"The stored password must have the form 'salt$hash'");
		}
		String hashOfInput = hash(password, Base64.getDecoder().decode((saltAndHash[0])));
		return hashOfInput.equals(saltAndHash[1]);
	}
	/** helper function to insert login timestamp into login database
	 * @throws SQLException
	 *
	 */
	private static void addLoginTimestamp() throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", "mr_admin", "mr_password");
		String insertLoginTimestamp =
				"INSERT INTO Login "
						+ "(SessionID, E_ID, Privilege, LoginTime) "
						+ "VALUES (?, ?, ?, ?);";
		PreparedStatement ps = erpDB.prepareStatement(insertLoginTimestamp);
		// generate sessionid
		currentSessionID = UUID.randomUUID();
		int i = 1;
		ps.setObject(i++, currentSessionID);
		ps.setString(i++, databaseUsername);
		ps.setString(i++, UIController.getRole(databaseUsername));

		// create anonymous date instance, get current time
		long currentTime = new Date().getTime();

		ps.setTimestamp(i++, new Timestamp(currentTime));

		ps.executeUpdate();

		ps.close();
		erpDB.close();
		System.out.println("Login attempt " + currentSessionID.toString() +
				" logged for user " + databaseUsername + " on " + new Date(currentTime).toString());
	}

	/**
	 * Called when login submit button is pushed
	 * Searches for username in employee database, then checks if passwords match
	 * @throws SQLException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean login(String username, String password) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", "mr_admin", "mr_password");
		String passwordQuery =
				"SELECT password "
						+ "FROM employee "
						+ "WHERE E_ID = ?;";
		PreparedStatement passwordStatement = erpDB.prepareStatement(passwordQuery);
		passwordStatement.setString(1, username);
		ResultSet storedPassword = passwordStatement.executeQuery();
		try {
			if (storedPassword.next() && check(password, storedPassword.getString("password"))) {
				databaseUsername = username;
				databasePassword = password;
				UIController.addLoginTimestamp();
				storedPassword.close();
				passwordStatement.close();
				erpDB.close();
				return true;
			} else {
				return false;
			}
		} catch(NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.err.println("Invalid password entered!");
			return false;
		}

	}

	/**
	 * adds logout timestamp to current session in login table
	 * WARNING: Only run after login function has been run! Depopulates the databaseUsername, databasePassword
	 * and currentSessionID fields, so very few commands can be run in a logged out state
	 * @throws SQLException
	 */
	public static void logout() throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", "mr_admin", "mr_password");
		String insertLogoutTimestamp =
				"UPDATE login "
						+ "SET logoutTime = ? "
						+ "WHERE SessionID = ?;";

		PreparedStatement ps = erpDB.prepareStatement(insertLogoutTimestamp);
		long currentTime = new Date().getTime();
		ps.setTimestamp(1, new Timestamp(currentTime));
		ps.setObject(2, currentSessionID);
		ps.executeUpdate();

		ps.close();
		erpDB.close();
		if (currentSessionID == null ) {
			System.out.println("Null session ID! This shouldn't happen!");
		} else {
			System.out.println("Session " + currentSessionID.toString() +
					" logged out for user " + databaseUsername + " on " + new Date(currentTime).toString());
		}
		// resetting session variables
		currentSessionID = null;
		databaseUsername = null;
		databasePassword = null;
	}

	/**
	 * call to retrieve role of user based on e_id
	 * @throws SQLException
	 *
	 */
	public static String getRole(String E_ID) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", "mr_admin", "mr_password");
		String selectRole =
				"SELECT JobType from employee "
						+ "WHERE E_ID = ?;";

		PreparedStatement ps = erpDB.prepareStatement(selectRole);
		ps.setString(1, E_ID);
		ResultSet rs = ps.executeQuery();

		if(rs.next()) {
			String str = rs.getString("JobType");
			rs.close();
			ps.close();
			erpDB.close();
			return str;
		} else {
			rs.close();
			ps.close();
			erpDB.close();
			System.err.println("E_ID not found!");
			return null;
		}
	}

	/**
	 * Adds employee to the database
	 * @throws SQLException
	 *
	 */
	public static void addEmployee(String E_ID, String password, String firstName, String lastName, String ssn, float Salary, Boolean isHourly, String jobType) throws SQLException {
		try {
			Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", "mr_admin", "mr_password");
			String employeeInfo =
					"INSERT INTO employee "
							+ "(E_ID, Password, FirstName, LastName, SSN, Salary, isHourly, jobType) "
							+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement ps = erpDB.prepareStatement(employeeInfo);
			int i = 1;
			ps.setString(i++, E_ID);
			ps.setString(i++, UIController.getSaltedHash(password));
			ps.setString(i++, firstName);
			ps.setString(i++, lastName);
			ps.setString(i++, ssn);
			ps.setFloat(i++, Salary);
			ps.setBoolean(i++, isHourly);
			ps.setString(i++, jobType);

			ps.executeUpdate();
			System.out.println("Employee " + E_ID + " added to employee database");

			Statement st = erpDB.createStatement();

			// create user login
			String assignRole = "CREATE USER \"" + E_ID + "\" WITH INHERIT PASSWORD '" + password + "';";
			st.executeUpdate(assignRole);
			System.out.println("Employee " + E_ID + "'s login and password have been created");

			// grant privileges
			assignRole = "GRANT " + jobType + " TO \"" + E_ID + "\";";
			st.execute(assignRole);
			System.out.println("Employee " + E_ID + " has been given the " + jobType + " role");

			st.close();
			ps.close();
			erpDB.close();

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.err.println("Invalid password!");
		}
	}

	/**
	 * updates selected employee's password
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static void updatePassword(String password, String E_ID) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String changePassword =
				"UPDATE Employee "
						+ "SET password = ? "
						+ "WHERE E_ID = ?;";

		PreparedStatement ps = erpDB.prepareStatement(changePassword);
		ps.setString(1, UIController.getSaltedHash(password));
		ps.setString(2, E_ID);
		ps.executeUpdate();
		System.out.println("Password updated!");

		erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", "mr_admin", "mr_password");
		String changeRolePassword = "ALTER ROLE \"" + E_ID + "\" WITH PASSWORD '" + password + "';";
		ps = erpDB.prepareStatement(changeRolePassword);
		ps.execute();
		System.out.println("Role password updated!");
	}

	/**
	 * deletes all employees and roles from database
	 * warning: will not work if employee table is not populated with role!
	 * make sure employee table is synced with the roles present
	 * @throws SQLException
	 */
	public static void deleteAllEmployees() throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", "mr_admin", "mr_password");
		String retrieveEmployees = "SELECT E_ID FROM employee;";
		Statement st = erpDB.createStatement();
		Statement dropRole = erpDB.createStatement();
		ResultSet rs = st.executeQuery(retrieveEmployees);

		while(rs.next()) {
			String currentRole = rs.getString("E_ID");
			dropRole.execute("DROP ROLE IF EXISTS \"" + currentRole + "\"");
			System.out.println("Dropped role " + currentRole);
		}

		System.out.println("All roles dropped");
		st.execute("DELETE FROM employee;");
		System.out.println("Employee table cleared");

		st.close();
		dropRole.close();
		erpDB.close();
	}

	/**
	 * adds model to the database
	 * @throws SQLException
	 *
	 */
	public static void addModel(String modelName, float salePrice) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String insertModel =
				"INSERT INTO model "
						+ "(modelName, salePrice) "
						+ "VALUES (?, ?);";

		PreparedStatement ps = erpDB.prepareStatement(insertModel);
		ps.setString(1, modelName);
		ps.setFloat(2, salePrice);
		ps.executeUpdate();

		ps.close();
		erpDB.close();
		System.out.println(modelName + " added to model database with a price of $" + salePrice);
	}

	/**
	 * adds customer to database
	 *
	 */
	public static void addCustomer(String firstName, String lastName, String C_ID) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String insertCustomer =
				"INSERT INTO customer "
						+ "(firstName, lastName, C_ID) "
						+ "VALUES (?, ?, ?);";

		PreparedStatement ps = erpDB.prepareStatement(insertCustomer);
		ps.setString(1, firstName);
		ps.setString(2, lastName);
		ps.setString(3, C_ID);
		ps.executeUpdate();

		System.out.println("Customer " + firstName + " " + lastName +
				" added to customer database with a C_ID of " + C_ID);

		ps.close();
		erpDB.close();
	}

	/**
	 * retrieves sales price from model table when given model name
	 * @throws SQLException
	 */
	public static float getSalePrice(String modelName) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String retrieveSalePrice =
				"SELECT SalePrice FROM model "
						+ "WHERE ModelName = ?;";

		PreparedStatement ps = erpDB.prepareStatement(retrieveSalePrice);
		ps.setString(1, modelName);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			float salePrice = rs.getFloat("SalePrice");
			rs.close();
			ps.close();
			erpDB.close();
			return salePrice;
		} else {
			rs.close();
			ps.close();
			erpDB.close();
			System.out.println("Model name not found!");
			return (float)0.00;
		}
	}

	/**
	 * adds order to database
	 * @throws SQLException
	 */
	public static void addOrder(String C_ID, String E_ID, String modelName, int Quantity) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String addOrderInfo =
				"INSERT INTO OrderInfo "
						+ "(C_ID, E_ID, ModelName, Quantity, SaleValue) "
						+ "VALUES (?, ?, ?, ?, ?);";

		PreparedStatement ps = erpDB.prepareStatement(addOrderInfo);
		int i = 1;
		ps.setString(i++, C_ID);
		ps.setString(i++, E_ID);
		ps.setString(i++, modelName);
		ps.setInt(i++, Quantity);
		ps.setFloat(i++, Quantity * UIController.getSalePrice(modelName));

		ps.executeUpdate();
		System.out.println("Order placed for " + Quantity + " " + modelName + "s priced at $" + (Quantity * UIController.getSalePrice(modelName)));

		UIController.updateInventoryQuantity(modelName, -Quantity);
		ps.close();
		erpDB.close();
	}

	/**
	 * add inventory to database
	 * @throws SQLException
	 */
	public static void addInventory(String modelName, float cost, int leadTime, String categoryType, int quantity) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String addInventoryInfo =
				"INSERT INTO Inventory "
						+ "(ModelName, Cost, Lead_time, Category_type, Quantity)"
						+ "VALUES (?, ?, ?, ?, ?);";

		PreparedStatement ps = erpDB.prepareStatement(addInventoryInfo);
		int i = 1;
		ps.setString(i++, modelName);
		ps.setFloat(i++, cost);
		ps.setInt(i++, leadTime);
		ps.setString(i++, categoryType);
		ps.setInt(i++, quantity);

		ps.executeUpdate();

		ps.close();
		erpDB.close();
		System.out.println("Inventory of " + modelName + " added with " + quantity + " item(s)");
	}

	/**
	 * retrieve quantity from inventory given the modelname
	 * @throws SQLException
	 */
	public static int getInventoryQuantity(String modelName) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String selectInventoryQuantity =
				"SELECT Quantity FROM Inventory "
						+ "WHERE ModelName = ?;";
		PreparedStatement ps = erpDB.prepareStatement(selectInventoryQuantity);
		ps.setString(1, modelName);

		ResultSet rs = ps.executeQuery();

		if(rs.next()) {
			int quantity = rs.getInt("Quantity");
			rs.close();
			ps.close();
			erpDB.close();
			return quantity;
		} else {
			return 0;
		}
	}

	/*
	 * update inventory quantity, amount is subtracted from current inventory
	 * @throws SQLException
	 */
	public static void updateInventoryQuantity(String modelName, int amount) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String updateInventory =
				"UPDATE Inventory "
						+ "SET Quantity = ? "
						+ "WHERE ModelName = ?;";
		PreparedStatement ps = erpDB.prepareStatement(updateInventory);
		ps.setString(2, modelName);
		ps.setInt(1, UIController.getInventoryQuantity(modelName) + amount);
		ps.executeUpdate();

		ps.close();
		erpDB.close();
		System.out.println("Inventory for " + modelName + " modified by " + amount);
	}

	/**
	 * deletes employee information based on e_id
	 * @throws SQLException
	 */
	public static void deleteEmployeeInformation(String E_ID) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", "mr_admin", "mr_password");
		PreparedStatement ps = erpDB.prepareStatement("DELETE FROM orderInfo WHERE E_ID = ?;");
		ps.setString(1, E_ID);
		ps.executeUpdate();
		System.out.println("Deleted orders associated with " + E_ID);

		ps = erpDB.prepareStatement("DELETE FROM employee WHERE E_ID = ?;");
		ps.setString(1, E_ID);
		ps.executeUpdate();
		System.out.println("Employee " + E_ID + " deleted from employee table");

		ps = erpDB.prepareStatement("DROP ROLE IF EXISTS \"" + E_ID + "\";");
		ps.execute();
		System.out.println("Dropped role " + E_ID);

		ps.close();
		erpDB.close();

	}

	/**
	 * displays certain employee information
	 * @throws SQLException
	 */
	public static String[] displayEmployeeInformation(String E_ID) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String selectEmployeeInformation =
				"SELECT firstName, lastName, SSN, Salary, isHourly, jobType "
						+ "FROM Employee "
						+ "WHERE E_ID = ?;";

		PreparedStatement ps = erpDB.prepareStatement(selectEmployeeInformation);
		ps.setString(1, E_ID);
		ResultSet rs = ps.executeQuery();

		String[] employeeInfo = new String[6];
		if(rs.next()) {
			employeeInfo[0] = rs.getString("firstName");
			employeeInfo[1] = rs.getString("lastName");
			employeeInfo[2] = rs.getString("SSN");
			employeeInfo[3] = Boolean.toString(rs.getBoolean("isHourly"));
			employeeInfo[4] = Float.toString(rs.getFloat("Salary"));
			employeeInfo[5] = rs.getString("jobType");
			return employeeInfo;
		} else {
			return null;
		}

	}

	/**
	 * updates employee information based on string array parameter
	 */
	public static void updateEmployeeInformation(String E_ID, String[] employeeInfo) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String selectEmployeeInformation =
				"UPDATE Employee "
						+ "SET firstName = ?, lastName = ?, SSN = ?, isHourly = ?, Salary = ?, jobType = ?"
						+ "WHERE E_ID = ?;";

		PreparedStatement ps = erpDB.prepareStatement(selectEmployeeInformation);
		System.out.println(Arrays.toString(employeeInfo));
		int i = 1;
		ps.setString(i++, employeeInfo[0]);
		ps.setString(i++, employeeInfo[1]);
		ps.setString(i++, employeeInfo[2]);
		ps.setBoolean(i++, Boolean.parseBoolean(employeeInfo[3]));
		ps.setFloat(i++, Float.parseFloat(employeeInfo[4]));
		ps.setString(i++, employeeInfo[5]);
		ps.setString(i++, E_ID);

		ps.executeUpdate();
		System.out.println("Updated!");
	}

	/**
	 * displays certain customer information
	 * @throws SQLException
	 */
	public static String[] displayCustomerInformation(String C_ID) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String selectCustomerInformation =
				"SELECT firstname, lastname"
						+ "FROM Customer "
						+ "WHERE C_ID = ?;";

		PreparedStatement ps = erpDB.prepareStatement(selectCustomerInformation);
		ps.setString(1, C_ID);
		ResultSet rs = ps.executeQuery();

		String[] customerInfo = new String[2];
		if(rs.next()) {
			customerInfo[0] = rs.getString("firstname");
			customerInfo[1] = rs.getString("lastname");
			return customerInfo;
		} else {
			return null;
		}

	}

	/**
	 * displays certain customer information
	 * @throws SQLException
	 */
	public static void updateCustomerInformation(String C_ID, String[] customerInfo) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String selectEmployeeInformation =
				"UPDATE Customer "
						+ "SET firstname = ?, lastname = ?"
						+ "WHERE C_ID = ?;";

		PreparedStatement ps = erpDB.prepareStatement(selectEmployeeInformation);
		System.out.println(Arrays.toString(customerInfo));
		int i = 1;
		ps.setString(i++, customerInfo[0]);
		ps.setString(i++, customerInfo[1]);
		ps.setString(i++, C_ID);

		ps.executeUpdate();
		System.out.println("Updated!");
	}

	/**
	 * display business report by querying the employeeRevenue and totalRevenue views
	 * @return formatted string with results of views queried
	 * @throws SQLException
	 */
	public static String displayBusinessReport() throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);

		Statement st = erpDB.createStatement();
		ResultSet employeeRevenue = st.executeQuery("SELECT * FROM EmployeeRevenue;");
		ResultSetMetaData rsmd = employeeRevenue.getMetaData();
		System.out.println("querying employeeRevenue");

		String str = "";

		while (employeeRevenue.next()) {
			if(rsmd.getColumnCount() >= 1) {
				str += "Employee " + employeeRevenue.getString("E_ID") +
						" has a total revenue of $" + employeeRevenue.getFloat("salesPerEmployee") + "\n";
			}
		}

		ResultSet totalRevenue = st.executeQuery("SELECT * FROM totalRevenue;");
		while (totalRevenue.next()) {
			str += "Total revenue generated this quarter: $" + totalRevenue.getFloat("totalSales");
		}

		employeeRevenue.close();
		st.close();
		erpDB.close();
		return str;
	}
	/**
	 * displays certain model information
	 * @throws SQLException
	 */
	public static String[] displayModelInformation(String mName) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String selectModelInformation =
				"SELECT modelnumber, productioncost, saleprice"
						+ "FROM Model "
						+ "WHERE modelname = ?;";

		PreparedStatement ps = erpDB.prepareStatement(selectModelInformation);
		ps.setString(1, mName);
		ResultSet rs = ps.executeQuery();

		String[] modelInfo = new String[3];
		if(rs.next()) {
			modelInfo[0] = rs.getString("modelnumber");
			modelInfo[1] = rs.getString("productioncost");
			modelInfo[2] = rs.getString("saleprice");

			return modelInfo;
		} else {
			return null;
		}

	}

	/**
	 * displays certain model information
	 * @throws SQLException
	 */
	public static void updateModelInformation(String mName, String[] modelInfo) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String selectModelInformation =
				"UPDATE Model "
						+ "SET modelnumber = ?, productioncost = ?, saleprice = ?"
						+ "WHERE modelname = ?;";

		PreparedStatement ps = erpDB.prepareStatement(selectModelInformation);
		System.out.println(Arrays.toString(modelInfo));
		int i = 1;
		ps.setString(i++, modelInfo[0]);
		ps.setString(i++, modelInfo[1]);
		ps.setString(i++, modelInfo[2]);
		ps.setString(i++, mName);

		ps.executeUpdate();
		System.out.println("Updated!");
	}
	/**
	 * displays certain model information
	 * @throws SQLException
	 */
	public static String[] displayInventoryInformation(String iid) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String selectInventoryInformation =
				"SELECT modelname, cost, lead_time, category_type, quantity"
						+ "FROM Inventory "
						+ "WHERE i_id = ?;";

		PreparedStatement ps = erpDB.prepareStatement(selectInventoryInformation);
		ps.setString(1, iid);
		ResultSet rs = ps.executeQuery();

		String[] invenInfo = new String[5];
		if(rs.next()) {
			invenInfo[0] = rs.getString("modelname");
			invenInfo[1] = rs.getString("cost");
			invenInfo[2] = rs.getString("lead_time");
			invenInfo[3] = rs.getString("category_type");
			invenInfo[4] = rs.getString("quantity");

			return invenInfo;
		} else {
			return null;
		}

	}

	/**
	 * displays certain model information
	 * @throws SQLException
	 */
	public static void updateInventoryInformation(String iid, String[] invenInfo) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String selectInventoryInformation =
				"UPDATE Inventory "
						+ "SET modelname = ?, cost = ?, lead_time = ?, category_type = ?, quantity = ?"
						+ "WHERE iid = ?;";

		PreparedStatement ps = erpDB.prepareStatement(selectInventoryInformation);
		System.out.println(Arrays.toString(invenInfo));
		int i = 1;
		ps.setString(i++, invenInfo[0]);
		ps.setString(i++, invenInfo[1]);
		ps.setString(i++, invenInfo[2]);
		ps.setString(i++, invenInfo[3]);
		ps.setString(i++, invenInfo[4]);
		ps.setString(i++, iid);

		ps.executeUpdate();
		System.out.println("Updated!");
	}
	/**
	 * displays specific engineer accessed information
	 * @throws SQLException
	 */
	public static String[] engineerAccess(String eid) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String selectEmployeeInformation =
				"SELECT firstName, lastName, jobType "
						+ "FROM Employee "
						+ "WHERE E_ID = ?;";

		PreparedStatement ps = erpDB.prepareStatement(selectEmployeeInformation);
		ps.setString(1, eid);
		ResultSet rs = ps.executeQuery();

		String[] employeeInfo = new String[3];
		if(rs.next()) {
			employeeInfo[0] = rs.getString("firstName");
			employeeInfo[1] = rs.getString("lastName");
			employeeInfo[2] = rs.getString("jobType");
			return employeeInfo;
		} else {
			return null;
		}
	}
	/**
	 * displays specific hr accessed information
	 * @throws SQLException
	 */
	public static String [] hrAccess(String eid) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String selectEmployeeInformation =
				"SELECT firstName, lastName "
						+ "FROM Employee "
						+ "WHERE E_ID = ?;";

		PreparedStatement ps = erpDB.prepareStatement(selectEmployeeInformation);
		ps.setString(1, eid);
		ResultSet rs = ps.executeQuery();

		String[] employeeInfo = new String[3];
		if(rs.next()) {
			employeeInfo[0] = rs.getString("firstName");
			employeeInfo[1] = rs.getString("lastName");
		} else {
			return null;
		}

		/*need code that adds total sales revenue added up from the sales
		that are in all orders associated with specified eid*/
		return employeeInfo;
	}
}
