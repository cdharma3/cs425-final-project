package GUIs;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;
@SuppressWarnings("unused")
public class HRMainPage extends JDialog {
	 
	    /**
	 * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");
	 */
	private static final long serialVersionUID = 1L;
	    private JButton btnEmplyeeInfo;			// Access and update employee info
	    private JButton btnSales;				// View employees and their sales numbers
	    private boolean succeeded;				// If successful
	 
	    public HRMainPage(Frame parent) {
	        super(parent, "Admin Main Page", true);
	        //
	        JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints cs = new GridBagConstraints();
	 
	        cs.fill = GridBagConstraints.HORIZONTAL;
	        
	        //employee id
	       
	        //Line separating buttons and welcome message
	        //Create new employee button and label
	        
	        btnEmplyeeInfo = new JButton("Access and Edit Employee Info");
	        btnEmplyeeInfo.addActionListener(new ActionListener() {
	 
	        	public void actionPerformed(ActionEvent e) {
	        		//TODO
	        		dispose();
	            }
	        });
	        panel.add(btnEmplyeeInfo);
	        
	        btnSales = new JButton("View Employees and Sales");
	        btnSales.addActionListener(new ActionListener() {
	        
	            public void actionPerformed(ActionEvent e) {
	            	//TODO
	                dispose();
	            }
	        });
	        panel.add(btnSales);
	        
	        getContentPane().add(panel, BorderLayout.CENTER);
	        getContentPane().add(panel, BorderLayout.PAGE_END);
	 
	        pack();
	        setResizable(false);
	        setLocationRelativeTo(parent);
	    }
		// * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");


	    public boolean isSucceeded() {
	        return succeeded;
	    }
	}