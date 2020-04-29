package database;

import java.sql.SQLException;

import GUIs.UIController;

public class CreateTestData {

	public static void main(String[] args) {

		try {
			// Create bob
			UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");

			// create jen
			UIController.addEmployee("jMcmurphy", "jen123", "Jen", "McMurphy", "987654321", (float)150000.00, false, "engineering");

			// create jon
			UIController.addEmployee("jWillis", "bigboi", "Jon", "Willis", "222222222", (float)15.00, true, "sales");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}