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
	    private JButton btnCreateCust;			// Create Employee Button
	    private JButton btnEdit;				// Edit data Button
	    private JButton btnOrder;				// Create Order
	    private JButton btnreports;         	// access and reports
	    private boolean succeeded;				// If successful
	 
	    public SalesMainPage(Frame parent) {
	        super(parent, "Admin Main Page", true);
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
	            	//TODO
	                dispose();
	            }
	        });
	        panel.add(btnEdit);
	        
	        btnOrder = new JButton("Grant Access");
	        btnOrder.addActionListener(new ActionListener() {
	        
	            public void actionPerformed(ActionEvent e) {
	            	//TODO
	                dispose();
	            }
	        });
	        panel.add(btnOrder);
	        
	        btnreports = new JButton("View and Create reports");
	        btnreports.addActionListener(new ActionListener() {
	        
	            public void actionPerformed(ActionEvent e) {
	            	//TODO
	                dispose();
	            }
	        });
	        panel.add(btnreports);
	 
	 
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