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
		private JLabel lbWelcome;				//Welcome message
	    private JButton btnCreateEmp;			// Create Employee Button
	    private JLabel lbCreateEmp;	
	    private JButton btnCancel;				// Cancel Button
	    private boolean succeeded;				// If successful
	 
	    public AdminMainPage(Frame parent) {
	        super(parent, "New Employee", true);
	        //
	        JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints cs = new GridBagConstraints();
	 
	        cs.fill = GridBagConstraints.HORIZONTAL;
	        
	        //employee id
	        lbWelcome = new JLabel("Welcome to the Admin Home screen");
	        cs.gridx = 0;
	        cs.gridy = 0;
	        cs.gridwidth = 1;
	        panel.add(lbWelcome, cs);
	       
	        //Line separating buttons and welcome message
	        panel.setBorder(new LineBorder(Color.GRAY));
	        
	        //Create new employee button and label
	        lbCreateEmp = new JLabel("Create a new Employee");
	        cs.gridx = 0;
	        cs.gridy = 0;
	        cs.gridwidth = 1;
	        panel.add(lbCreateEmp, cs);
	        
	        btnCreateEmp = new JButton("Create");
	        btnCreateEmp.addActionListener(new ActionListener() {
	 
	        	public void actionPerformed(ActionEvent e) {
	                MainCreateEmployee.main(null);
	                return;
	            }
	        });
	        cs.gridx = 1;
	        cs.gridy = 0;
	        cs.gridwidth = 2;
	        
	        //TODO
	        btnCancel = new JButton("Cancel");
	        btnCancel.addActionListener(new ActionListener() {
	 
	            public void actionPerformed(ActionEvent e) {
	                dispose();
	            }
	        });
	        JPanel bp = new JPanel();
	        bp.add(btnCreateEmp);
	        bp.add(btnCancel);
	 
	        getContentPane().add(panel, BorderLayout.CENTER);
	        getContentPane().add(bp, BorderLayout.PAGE_END);
	 
	        pack();
	        setResizable(false);
	        setLocationRelativeTo(parent);
	    }
		// * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");


	    public boolean isSucceeded() {
	        return succeeded;
	    }
	}