package GUIs;
//Run this to Start Login
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
 
@SuppressWarnings("unused")
public class MainLogin {
    public static boolean quit = false;

	public static void main(String[] args) {
        final JFrame frame = new JFrame("Database Login");
        final JButton btnLogin = new JButton("Click to login");
 
        btnLogin.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                    	frame.dispose();
                        LoginDialog loginDlg = new LoginDialog(frame);
                        loginDlg.setVisible(true);
                        // if login successfully
                        if(loginDlg.isSucceeded()){
                            btnLogin.setText("Welcome " + loginDlg.getUsername() + "!");
                            frame.dispose();
                            // MainCreateCustomer.main(null);
                            RoleGUI(loginDlg.getUsername());
                        }
                    }
                });
 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnLogin);
        frame.setVisible(true);
    }
    
	public static void RoleGUI(String username) {
        final JFrame frame = new JFrame("Welcome " + username + "!");
        final JButton btnEnter = new JButton("Click to enter home screen");
        try {
			username = UIController.getRole(username);
		} catch (SQLException e1) {
			System.out.println("invalid username");
		}
		if (username.equals("admin")){
			AdminPage.main(null);
			frame.dispose();
		} else if(username.equals("hr")){
			HRPage.main(null);
		} else if(username.equals("sales")){
			SalesPage.main(null);
		} else if(username.equals("engineering")) {
			EngPage.main(null);
		} else {
			System.out.println("Invalid Job type: "+username+". Please confirm correct Role is assigned.");
		}
	}
}
    