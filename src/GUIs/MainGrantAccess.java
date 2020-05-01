package GUIs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("unused")
public class MainGrantAccess {
	public static void main(String[] args) {
		//testing
		try {
			UIController.login("bWatts","abc123");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//testing
        final JFrame frame = new JFrame("Grant Access to an Employee");
        final JButton btnOrder = new JButton("Click to Begin");
 
        btnOrder.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                    	frame.dispose();
                        GrantAccessDialog OrderDlg = new GrantAccessDialog(frame);
                        OrderDlg.setVisible(true);
                        return;
                        
                    }
                });
 
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(400, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnOrder);
        frame.setVisible(true);
    }
}
