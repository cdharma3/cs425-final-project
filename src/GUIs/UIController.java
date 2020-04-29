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

	/** Checks whether given plaintext password corresponds to a stored salted hash of the password.
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

	/** Called when login submit button is pushed
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
		storedPassword.next();
		try {
			if (check(password, storedPassword.getString("password"))) {
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

	/** Adds employee to the database
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
			String assignRole = "CREATE USER " + E_ID + " WITH ENCRYPTED PASSWORD '" + password + "';";
			st.executeUpdate(assignRole);
			System.out.println("Employee " + E_ID + "'s login and password have been created");

			// grant privileges
			assignRole = "GRANT " + jobType + " TO " + E_ID + ";";
			st.execute(assignRole);
			System.out.println("Employee " + E_ID + " has been given the " + jobType + " role");

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.err.println("Invalid password!");
		}
	}

}
