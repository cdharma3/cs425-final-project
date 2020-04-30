package GUIs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ViewBusinessReportDialog extends JDialog {
	//new vars
	private static JTextArea area;
	private static JFrame f;
	private static JScrollPane sPane;
    private JButton btnCancel;				// Cancel Button
    private JButton btnLogout;				// Logout Button
    
    ViewBusinessReportDialog() throws SQLException {
    	
        //business reports code
        
        f = new JFrame();
        try {
			area=new JTextArea(UIController.displayBusinessReport());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

		sPane = new JScrollPane(area);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
        area.setBounds(10,30, 200,200);  
        f.add(area);
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminPage.main(null);
            }
        });
        btnLogout = new JButton("Logout");
        btnLogout.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent e) {
            	try {
					UIController.logout();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnCancel);
        bp.add(btnCancel);
        
        f.setSize(300,300);  
        f.setLayout(null);  
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public static void main(String args[]) throws SQLException  
    {  
    	new ViewBusinessReportDialog();  
    }  
}
