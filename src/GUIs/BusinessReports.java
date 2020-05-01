package GUIs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class BusinessReports {
	public static void main(String[] args) {
        final JFrame frame = new JFrame("Business Report");
        final JButton btnRev = new JButton("Generate Report");
        final JButton btnLogout = new JButton("Logout");
        final JButton btnCancel = new JButton("Cancel");
        btnRev.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        try {
							BusinessReportsDialog.main(null);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
                        
                    }
                });
        btnLogout.addActionListener(
        		new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				try {
        					UIController.logout();
        				} catch (SQLException e1) {
    						e1.printStackTrace();
    					}
    	            	frame.dispose();
    	            }
        					
        		});
        btnCancel.addActionListener(
        		new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				frame.dispose();
						AdminPage.main(null);
    	            }
        					
        		});
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(400, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnRev);
        frame.getContentPane().add(btnLogout);
        frame.getContentPane().add(btnCancel);
        frame.setVisible(true);
	}
}
