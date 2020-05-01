package GUIs;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;
@SuppressWarnings("unused")
public class CreateCustomerDialog extends JDialog {
	 
	private static final long serialVersionUID = 1L;
		private JTextField tfcid;				// Customer ID
		private JLabel lbcid;
	    private JTextField tffname;				// First Name
	    private JLabel lbfname;
	    private JTextField tflname;				// Last Name
	    private JLabel lblname;
	    private JButton btnLogout;				//Logout
	    private JButton btnEnter;				// Enter Button
	    private JButton btnCancel;				// Cancel Button
	    private boolean succeeded;				// If successful
	 
	    public CreateCustomerDialog(Frame parent) {
	        super(parent, "New Employee", true);
	        //
	        JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints cs = new GridBagConstraints();
	 
	        cs.fill = GridBagConstraints.HORIZONTAL;
	        
	        //employee id
	        lbcid = new JLabel("Customer ID: ");
	        cs.gridx = 0;
	        cs.gridy = 0;
	        cs.gridwidth = 1;
	        panel.add(lbcid, cs);
	        
	        tfcid = new JTextField(50);
	        cs.gridx = 1;
	        cs.gridy = 0;
	        cs.gridwidth = 2;
	        panel.add(tfcid, cs);
	        
	        //First Name
	        lbfname = new JLabel("First Name: ");
	        cs.gridx = 0;
	        cs.gridy = 1;
	        cs.gridwidth = 1;
	        panel.add(lbfname, cs);
	        
	        tffname = new JTextField(50);
	        cs.gridx = 1;
	        cs.gridy = 1;
	        cs.gridwidth = 2;
	        panel.add(tffname, cs);
	        
	        //Last Name
	        lblname = new JLabel("Last Name: ");
	        cs.gridx = 0;
	        cs.gridy = 2;
	        cs.gridwidth = 1;
	        panel.add(lblname, cs);
	        
	        tflname = new JTextField(50);
	        cs.gridx = 1;
	        cs.gridy = 2;
	        cs.gridwidth = 2;
	        panel.add(tflname, cs);

	        panel.setBorder(new LineBorder(Color.GRAY));
	 
	        btnEnter = new JButton("Enter");
	 
	        btnEnter.addActionListener(new ActionListener() {
	 
	        	public void actionPerformed(ActionEvent e) {
	                try {
	                	System.out.println("Attempting to add "+getFname()+" "+getLname()+" with Cid: "+getCid());
	                	UIController.addCustomer(getFname(), getLname(), getCid());
	                	JOptionPane.showMessageDialog(CreateCustomerDialog.this,
					            "Customer created successfully!",
					            "Create new Customer",
					            JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						System.err.println(e1.toString());
						JOptionPane.showMessageDialog(CreateCustomerDialog.this,
					            "Error creating customer, please try again",
					            "Create new employee",
					            JOptionPane.ERROR_MESSAGE);
					}
	                
	                dispose();
	                SalesPage.main(null);
	            }
	        });
	        btnCancel = new JButton("Cancel");
	        btnCancel.addActionListener(new ActionListener() {
	 
	            public void actionPerformed(ActionEvent e) {
	                dispose();
	                SalesPage.main(null);
	            }
	        });
	        JPanel bp = new JPanel();
	        bp.add(btnEnter);
	        bp.add(btnCancel);
	        
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
	        bp.add(btnLogout);
	 
	        getContentPane().add(panel, BorderLayout.CENTER);
	        getContentPane().add(bp, BorderLayout.PAGE_END);
	 
	        pack();
	        setResizable(false);
	        setLocationRelativeTo(parent);
	        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    }
		// * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");

	    public String getCid() {
	        return tfcid.getText().trim();
	    }
	 
	    public String getFname() {
	    	return tffname.getText().trim();
	    }
	    
	    public String getLname() {
	    	return tflname.getText().trim();
	    }

	    public boolean isSucceeded() {
	        return succeeded;
	    }
	}