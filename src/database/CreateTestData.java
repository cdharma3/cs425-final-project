package database;

import java.sql.SQLException;

import GUIs.UIController;

public class CreateTestData {

	public static void main(String[] args) {

		try {

			// Create bob
			UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "admin");

			// create jen
			UIController.addEmployee("jMcmurphy", "jen123", "Jen", "McMurphy", "987654321", (float)150000.00, false, "engineering");

			// create jon
			UIController.addEmployee("jWillis", "bigboi", "Jon", "Willis", "222222222", (float)15.00, true, "sales");

			// login as bob
			System.out.println("Logging in as bWatts returns : " + UIController.login("bWatts", "abc123"));

			// retrieve model number for model Swiffer_Duster
			System.out.println("Sale price for swiffer duster " + UIController.getSalePrice("Swiffer Duster"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
