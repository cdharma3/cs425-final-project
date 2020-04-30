package GUIs;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;
@SuppressWarnings("unused")
public class AdminMainPage extends JDialog {
	 
	    /**
	 * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");
	 */
	private static final long serialVersionUID = 1L;
	    private JButton btnCreateEmp;			// Create Employee Button
	    private JButton btnEdit;				// Edit data Button
	    private JButton btnAccess;				// Grant Access button
	    private JButton btnreports;         	// access and create reports
	    private JButton btnLogout;				//Logout
	    private boolean succeeded;				// If successful
	 
	    public AdminMainPage(Frame parent) {
	        super(parent, "Admin Main Page", true);
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
	        
	        btnEdit = new JButton("Edit data within tables");
	        btnEdit.addActionListener(new ActionListener() {
	        
	            public void actionPerformed(ActionEvent e) {
	            	//TODO
	                dispose();
	            }
	        });
	        panel.add(btnEdit);
	        
	        btnAccess = new JButton("Grant Access");
	        btnAccess.addActionListener(new ActionListener() {
	        
	            public void actionPerformed(ActionEvent e) {
	            	//TODO
	                dispose();
	            }
	        });
	        panel.add(btnAccess);
	        
	        btnreports = new JButton("View and Create reports");
	        btnreports.addActionListener(new ActionListener() {
	        
	            public void actionPerformed(ActionEvent e) {
	            	BusinessReports.main(null);
	                dispose();
	            }
	        });
	        panel.add(btnreports);
	        
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