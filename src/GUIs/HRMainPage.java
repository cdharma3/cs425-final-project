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
	    private JButton btnCreateEmp;			// Create Employee Button
	    private JButton btnLogout;				//Logout
	    private boolean succeeded;				// If successful
	 
	    public HRMainPage(Frame parent) {
	        super(parent, "HR Main Page", true);
	        //
	        JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints cs = new GridBagConstraints();
	 
	        cs.fill = GridBagConstraints.HORIZONTAL;
	        
	        //employee id
	       
	        //Line separating buttons and welcome message
	        //Create new employee button and label
	        btnCreateEmp = new JButton("Create a new Employee");
	        btnCreateEmp.addActionListener(new ActionListener() {
	 
	        	public void actionPerformed(ActionEvent e) {
	        		dispose();
	                MainCreateEmployee.main(null);
	            }
	        });
	        panel.add(btnCreateEmp);
	        
	        btnEmplyeeInfo = new JButton("Access and Edit Employee Info");
	        btnEmplyeeInfo.addActionListener(new ActionListener() {
	 
	        	public void actionPerformed(ActionEvent e) {
	        		dispose();
	        		EditEmployee.main(null);
	            }
	        });
	        panel.add(btnEmplyeeInfo);
	        
	        btnSales = new JButton("View Employees and Sales");
	        btnSales.addActionListener(new ActionListener() {
	        
	            public void actionPerformed(ActionEvent e) {
	                dispose();
	                HRViewEmployee.main(null);
	            }
	        });
	        panel.add(btnSales);
	        
	        btnLogout = new JButton("Logout");
	        btnLogout.addActionListener(new ActionListener() {
	        
	            public void actionPerformed(ActionEvent e) {
	            	try {
						UIController.logout();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
	            	dispose();
	            }
	        });
	        panel.add(btnLogout);
	        
	        getContentPane().add(panel, BorderLayout.CENTER);
	        getContentPane().add(panel, BorderLayout.PAGE_END);
	 
	        pack();
	        setResizable(false);
	        setLocationRelativeTo(parent);
	        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    }
		// * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");


	    public boolean isSucceeded() {
	        return succeeded;
	    }
	}