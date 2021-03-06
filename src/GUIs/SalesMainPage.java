package GUIs;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;
@SuppressWarnings("unused")
public class SalesMainPage extends JDialog {
	 
	    /**
	 * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");
	 */
	private static final long serialVersionUID = 1L;
	    private JButton btnCreateCust;			// Create Customer Button
	    private JButton btnEdit;				// Edit data Button
	    private JButton btnOrder;				// Create Order
	    private JButton btnreports;         	// access and reports
	    private JButton btnAdmin;
	    private JButton btnLogout;				//Logout
	    private boolean succeeded;				// If successful
	 
	    public SalesMainPage(Frame parent) {
	        super(parent, "Sales Main Page", true);
	        //
	        JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints cs = new GridBagConstraints();
	 
	        cs.fill = GridBagConstraints.HORIZONTAL;
	        
	        //employee id
	       
	        //Line separating buttons and welcome message
	        //Create new employee button and label
	        
	        btnCreateCust = new JButton("Create a new Customer");
	        btnCreateCust.addActionListener(new ActionListener() {
	 
	        	public void actionPerformed(ActionEvent e) {
	        		dispose();
	                MainCreateCustomer.main(null);
	            }
	        });
	        panel.add(btnCreateCust);
	        
	        btnEdit = new JButton("View and update customer data");
	        btnEdit.addActionListener(new ActionListener() {
	        
	            public void actionPerformed(ActionEvent e) {
	                dispose();
	                EditCustomer.main(null);
	            }
	        });
	        panel.add(btnEdit);
	        
	        btnOrder = new JButton("Create Order");
	        btnOrder.addActionListener(new ActionListener() {
	        
	            public void actionPerformed(ActionEvent e) {
	            	MainOrderForm.main(null);
	                dispose();
	            }
	        });
	        panel.add(btnOrder);
	        
	        btnreports = new JButton("View and Create reports");
	        btnreports.addActionListener(new ActionListener() {
	        
	            public void actionPerformed(ActionEvent e) {
	            	ViewbusinessReport.main(null);
	                dispose();
	            }
	        });
	        panel.add(btnreports);
	 
	        if(MainLogin.getisAdmin()) {
		        btnAdmin = new JButton("Return to Admin page");
		        btnAdmin.addActionListener(new ActionListener() {
		        
		            public void actionPerformed(ActionEvent e) {
		                dispose();
		                AdminPage.main(null);
		            }
		        });
		        panel.add(btnAdmin);
	        }
	        
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