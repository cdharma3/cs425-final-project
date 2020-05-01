package database;

import java.sql.SQLException;
import java.util.Arrays;

import GUIs.UIController;

public class CreateTestData {

	public static void main(String[] args) {

		try {

			// clear employee roles
			UIController.deleteAllEmployees();

			// Create bob
			UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "admin");

			// create jen
			UIController.addEmployee("jMcmurphy", "jen123", "Jen", "McMurphy", "987654321", (float)150000.00, false, "engineering");

			// create jon
			UIController.addEmployee("jWillis", "bigboi", "Jon", "Willis", "222222222", (float)15.00, true, "hr");

			UIController.addEmployee("a", "a", "a", "a", "222222222", (float)15.00, true, "admin");
			UIController.addEmployee("h", "h", "h", "h", "222222222", (float)15.00, true, "hr");
			UIController.addEmployee("s", "s", "s", "s", "222222222", (float)15.00, true, "sales");
			UIController.addEmployee("e", "e", "e", "e", "222222222", (float)15.00, true, "engineering");
			
			// login as bob
			System.out.println("Logging in returns: " + UIController.login("bWatts", "abc123"));
			UIController.logout();

			System.out.println("Logging in returns: " + UIController.login("bWatts", "abc123"));

			UIController.addCustomer("Jon", "Johnson", "jJohnson");
			UIController.addCustomer("c", "c", "c");
			String[] customerInfo = new String[2];
			customerInfo [0] = "Jon";
			customerInfo [1] = "Jonson";

			UIController.updateCustomerInformation("jJohnson", customerInfo);

			UIController.addModel("Swiffer Duster", (float)60.00, (float)120.00);
			UIController.addModel("Dyson Cyclone", (float)60.00, (float)240.00);
			UIController.addModel("Kenmore Elite Pet Friendly 31150", (float)60.00, (float)329.90);
			UIController.addModel("Shark Navigator Powered Lift-Away NV586", (float)60.00, (float)249.99);
			UIController.addModel("Miele Complete C3 Marin", (float)60.00, (float)1099.00);
			UIController.addModel("Miele Blizzard CX1 Cat & Dog", (float)60.00, (float)899.00);
			UIController.addModel("Shark APEX UpLight Lift-Away DuoClean LZ601", (float)60.00, (float)299.99);
			UIController.addModel("Eufy 11S", (float)60.00, (float)329.90);

			UIController.addInventory("Swiffer Duster", (float)120.00, 5, "Vacuum", 10);
			UIController.addInventory("Dyson Cyclone", (float)240.00, 20, "Vacuum", 20);

			UIController.addOrder("jJohnson", "bWatts", "Swiffer Duster", 5);
			UIController.addOrder("jJohnson", "bWatts", "Swiffer Duster", 5);
			UIController.addOrder("jJohnson", "bWatts", "Dyson Cyclone", 15);


			System.out.println(UIController.displayBusinessReport());

			System.out.println(Arrays.deepToString(UIController.displayEmployeeInformation("jWillis")));
			UIController.login("bWatts", "abc123");
			UIController.logout();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}