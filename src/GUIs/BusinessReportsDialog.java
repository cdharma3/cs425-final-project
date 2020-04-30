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
import javax.swing.border.TitledBorder;

public class BusinessReportsDialog extends JDialog {
	//new vars
	private static JTextArea area;
	private static JFrame f;
	private static JScrollPane sPane;
    
    BusinessReportsDialog() throws SQLException {
    	
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
        f.setSize(300,300);  
        f.setLayout(null);  
        f.setVisible(true);
    }
    public static void main(String args[]) throws SQLException  
    {  
    	UIController.login("bWatts", "abc123");
    	new BusinessReportsDialog();  
    }  
}
