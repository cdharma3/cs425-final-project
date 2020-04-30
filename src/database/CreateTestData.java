package database;

import java.sql.SQLException;

import GUIs.UIController;

public class CreateTestData {

	public static void main(String[] args) {

		try {

			// clear employee roles
			//UIController.deleteAllEmployees();

			// Create bob
			//UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "admin");

			// create jen
			//UIController.addEmployee("jMcmurphy", "jen123", "Jen", "McMurphy", "987654321", (float)150000.00, false, "engineering");

			// create jon
			//UIController.addEmployee("jWillis", "bigboi", "Jon", "Willis", "222222222", (float)15.00, true, "hr");

			// login as bob
			System.out.println("Logging in returns: " + UIController.login("bWatts", "abc123"));


			//UIController.addCustomer("Jon", "Johnson", "jJohnson");
			//UIController.addModel("Swiffer Duster", (float)120.00);
			//UIController.addModel("Dyson Cyclone", (float)240.00);
			//UIController.addInventory("Swiffer Duster", (float)120.00, 5, "Vacuum", 10);
			//UIController.addInventory("Dyson Cyclone", (float)240.00, 20, "Vacuum", 20);
			//UIController.addOrder("jJohnson", "bWatts", "Swiffer Duster", 5);
			//UIController.addOrder("jJohnson", "bWatts", "Swiffer Duster", 5);
			//UIController.addOrder("jJohnson", "bWatts", "Dyson Cyclone", 15);
			//UIController.addOrder("jJohnson", "jWillis", "Swiffer Duster", 5);
			//UIController.addOrder("jJohnson", "jMcmurphy", "Swiffer Duster", 5);
			//UIController.addOrder("jJohnson", "jMcmurphy", "Dyson Cyclone", 15);

			System.out.println(UIController.displayBusinessReport());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}