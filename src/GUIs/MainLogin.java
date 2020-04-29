package GUIs;
//Run this to Start Login
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class MainLogin {
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Database Login");
        final JButton btnLogin = new JButton("Click to login");
 
        btnLogin.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        LoginDialog loginDlg = new LoginDialog(frame);
                        loginDlg.setVisible(true);
                        // if login successfully
                        if(loginDlg.isSucceeded()){
                            btnLogin.setText("Welcome " + loginDlg.getUsername() + "!");
                            frame.dispose();
                           // MainCreateCustomer.main(null);
                        }
                    }
                });
 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnLogin);
        frame.setVisible(true);
    }
}