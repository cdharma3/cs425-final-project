package GUIs;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UIController {
	private static final int iterations = 20*1000;
	private static final int saltLen = 32;
	private static final int desiredKeyLen = 256;
	private static String databaseUsername = "";
	private static String databasePassword = "";

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
			return rs.getString("JobType");
		} else {
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
			return rs.getFloat("SalePrice");
		} else {
			System.out.println("Model name not found!");
			return (float)0.00;
		}
	}

	/**
	 * adds order to database
	 * @throws SQLException
	 */
	public static void addOrder(String C_ID, String E_ID, String ModelName, int Quantity) throws SQLException {
		Connection erpDB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/final-project-db", databaseUsername, databasePassword);
		String addOrderInfo =
				"INSERT INTO OrderInfo "
						+ "(C_ID, E_ID, ModelName, Quantity, SaleValue) "
						+ "VALUES (?, ?, ?, ?, ?);";

		PreparedStatement ps = erpDB.prepareStatement(addOrderInfo);
		int i = 1;
		ps.setString(i++, C_ID);
		ps.setString(i++, E_ID);
		ps.setString(i++, ModelName);
		ps.setInt(i++, Quantity);
		ps.setFloat(i++, Quantity * UIController.getSalePrice(ModelName));

		ps.executeUpdate();
		System.out.println("Order placed for " + Quantity + " " + ModelName + "s priced at $" + (Quantity * UIController.getSalePrice(ModelName)));

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
			return rs.getInt("Quantity");
		} else {
			return 0;
		}
	}
}
