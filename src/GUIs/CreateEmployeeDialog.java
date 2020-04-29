package GUIs;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;
public class CreateEmployeeDialog extends JDialog {
	 
	    /**
	 * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");
	 */
	private static final long serialVersionUID = 1L;
		private JTextField tfeid;				// Employee ID/ Login Id
		private JLabel lbeid;
		private JPasswordField pfPassword;		// Password
		private JLabel lbPassword;
	    private JTextField tffname;				// First Name
	    private JLabel lbfname;
	    private JTextField tflname;				// Last Name
	    private JLabel lblname;
	    private JTextField tfssn; 				// SSN
	    private JLabel lbssn;
	    private JTextField tfsalary;			// Salary
	    private JLabel lbsalary;
	    private JCheckBox cbhourly;				// Is Hourly
	    //private JLabel lbhourly;
	    private JTextField tfjobtype;			// Job Type
	    private JLabel lbjobtype;
	    private JButton btnEnter;				// Enter Button
	    private JButton btnCancel;				// Cancel Button
	    private boolean succeeded;				// If successful
	 
	    public CreateEmployeeDialog(Frame parent) {
	        super(parent, "New Employee", true);
	        //
	        JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints cs = new GridBagConstraints();
	 
	        cs.fill = GridBagConstraints.HORIZONTAL;
	        
	        //employee id
	        lbeid = new JLabel("Employee ID: ");
	        cs.gridx = 0;
	        cs.gridy = 0;
	        cs.gridwidth = 1;
	        panel.add(lbeid, cs);
	        
	        tfeid = new JTextField(50);
	        cs.gridx = 1;
	        cs.gridy = 0;
	        cs.gridwidth = 2;
	        panel.add(tfeid, cs);
	        
	        //password
	        lbPassword = new JLabel("Password: ");
	        cs.gridx = 0;
	        cs.gridy = 1;
	        cs.gridwidth = 1;
	        panel.add(lbPassword, cs);
	        
	        pfPassword = new JPasswordField(20);
	        cs.gridx = 1;
	        cs.gridy = 1;
	        cs.gridwidth = 2;
	        panel.add(pfPassword, cs);
	        
	        //First Name
	        lbfname = new JLabel("First Name: ");
	        cs.gridx = 0;
	        cs.gridy = 2;
	        cs.gridwidth = 1;
	        panel.add(lbfname, cs);
	        
	        tffname = new JTextField(50);
	        cs.gridx = 1;
	        cs.gridy = 2;
	        cs.gridwidth = 2;
	        panel.add(tffname, cs);
	        
	        //Last Name
	        lblname = new JLabel("Last Name: ");
	        cs.gridx = 0;
	        cs.gridy = 3;
	        cs.gridwidth = 1;
	        panel.add(lblname, cs);
	        
	        tflname = new JTextField(50);
	        cs.gridx = 1;
	        cs.gridy = 3;
	        cs.gridwidth = 2;
	        panel.add(tflname, cs);
	        
	        //SSN
	        lbssn = new JLabel("SSN: ");
	        cs.gridx = 0;
	        cs.gridy = 4;
	        cs.gridwidth = 1;
	        panel.add(lbssn, cs);
	        
	        tfssn = new JTextField(9);
	        cs.gridx = 1;
	        cs.gridy = 4;
	        cs.gridwidth = 2;
	        panel.add(tfssn, cs);
	        
	        //Is hourly
	        /*lbhourly = new JLabel("Are they paid by the Hour?");
	        cs.gridx = 0;
	        cs.gridy = 5;
	        cs.gridwidth = 1;
	        panel.add(lbhourly, cs);
	        */
	        cbhourly = new JCheckBox("Are they paid Hourly?");
	        cs.gridx = 0;
	        cs.gridy = 5;
	        cs.gridwidth = 2;
	        panel.add(cbhourly, cs);
	 
	        //Salary
	        lbsalary = new JLabel("Pay: ");
	        cs.gridx = 0;
	        cs.gridy = 6;
	        cs.gridwidth = 1;
	        panel.add(lbsalary, cs);
	        
	        tfsalary = new JTextField(18);
	        cs.gridx = 1;
	        cs.gridy = 6;
	        cs.gridwidth = 2;
	        panel.add(tfsalary, cs);
	        
	        //Job type
	        lbjobtype = new JLabel("Department they work at: ");
	        cs.gridx = 0;
	        cs.gridy = 7;
	        cs.gridwidth = 1;
	        panel.add(lbjobtype, cs);
	        
	        tfjobtype = new JTextField(50);
	        cs.gridx = 1;
	        cs.gridy = 7;
	        cs.gridwidth = 2;
	        panel.add(tfjobtype, cs);
	        
	        panel.setBorder(new LineBorder(Color.GRAY));
	 
	        btnEnter = new JButton("Enter");
	 
	        btnEnter.addActionListener(new ActionListener() {
	 
	        	public void actionPerformed(ActionEvent e) {
	                try {
						UIController.addEmployee(getEid(), getPassword(), getFname(), getLname(), getSSN(), getSalary(), getisHourly(), getJobType());
						    
					} catch (HeadlessException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        });
	        btnCancel = new JButton("Cancel");
	        btnCancel.addActionListener(new ActionListener() {
	 
	            public void actionPerformed(ActionEvent e) {
	                dispose();
	            }
	        });
	        JPanel bp = new JPanel();
	        bp.add(btnEnter);
	        bp.add(btnCancel);
	 
	        getContentPane().add(panel, BorderLayout.CENTER);
	        getContentPane().add(bp, BorderLayout.PAGE_END);
	 
	        pack();
	        setResizable(false);
	        setLocationRelativeTo(parent);
	    }
		// * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");

	    public String getEid() {
	        return tfeid.getText().trim();
	    }
	 
	    public String getPassword() {
	        return new String(pfPassword.getPassword());
	    }
	    
	    public String getFname() {
	    	return tffname.getText().trim();
	    }
	    
	    public String getLname() {
	    	return tflname.getText().trim();
	    }
	    
	    public String getSSN() {
	    	return tfssn.getText().trim();
	    }
	    
	    public Float getSalary() {
			try {
				return Float.parseFloat(tfsalary.getText().trim());
			}
			catch (Exception e){
				JOptionPane.showMessageDialog(CreateEmployeeDialog.this,
			            "Invalid input for Float, try again",
			            "Create new employee",
			            JOptionPane.ERROR_MESSAGE);
				
			}
			return null;
	    }
	    
	    public Boolean getisHourly() {
			if(cbhourly.isSelected()) {
				return true;
			}
			else {
				return false;
			}
	    }
	    
	    public String getJobType() {
	    	String job = tfjobtype.getText().trim();
	    	if(job.equals("hr")||job.equals("human resources")) {
	    		job = "hr";
	    	}else if (job.equals("engineering")||job.equals("engineer") ) {
	    		job = "engineering";
	    	}else if (job.equals("sales")||job.equals("sale")) {
	    		job = "sales";
	    	}else {
	    		JOptionPane.showMessageDialog(CreateEmployeeDialog.this,
			            "Invalid input for Job type, try again",
			            "Create new employee",
			            JOptionPane.ERROR_MESSAGE);
	    		return null;
	    	}
	    	return job;
	    }
	 
	    public boolean isSucceeded() {
	        return succeeded;
	    }
	}