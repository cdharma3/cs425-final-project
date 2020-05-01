package GUIs;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
public class EditEmployeeDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lbeid, lbfname, lblname, lbssn, lbsalary, lbhourly, lbjob;
	JLabel lbeid2, lbfname2, lblname2, lbssn2, lbsalary2, lbhourly2, lbjob2;
	JTextField tffname, tflname, tfssn, tfsalary, tfhourly, tfjob;
	JButton btnEnter, btnCancel;
	
	public EditEmployeeDialog(Frame parent,String eid) throws SQLException {
		super(parent,"Edit Employee",true);
		String [] employee;
		String [] update = new String[6];
		employee = UIController.displayEmployeeInformation(eid);
		if(employee[0] == null) {
			
		}
		
		JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
        //employee id
        lbeid = new JLabel("Employee ID: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbeid, cs);
      
        lbeid2 = new JLabel(eid);
        cs.gridx = 2;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbeid2, cs);
        
        
        //first name
        lbfname = new JLabel("First Name: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbfname, cs);
        
        lbfname2 = new JLabel(employee[0]);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbfname2, cs);
        
        tffname = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(tffname, cs);
        //last name
        lblname = new JLabel("Last Name: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lblname, cs);
        
        lblname2 = new JLabel(employee[1]);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lblname2, cs);
        
        tflname = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(tflname, cs);
        //ssn
        lbssn = new JLabel("SSN: ");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbssn, cs);
        
        lbssn2 = new JLabel(employee[2]);
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbssn2, cs);
        
        tfssn = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 3;
        cs.gridwidth = 2;
        panel.add(tfssn, cs);
      //hourly
        lbhourly = new JLabel("Hourly: ");
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(lbhourly, cs);
        
        lbhourly2 = new JLabel(employee[3]);
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(lbhourly2, cs);
        
        tfhourly = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 4;
        cs.gridwidth = 2;
        panel.add(tfhourly, cs);
        //salary
        lbsalary = new JLabel("Salary: ");
        cs.gridx = 0;
        cs.gridy = 5;
        cs.gridwidth = 1;
        panel.add(lbsalary, cs);
        
        lbsalary2 = new JLabel(employee[4]);
        cs.gridx = 1;
        cs.gridy = 5;
        cs.gridwidth = 1;
        panel.add(lbsalary2, cs);
        
        tfsalary = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 5;
        cs.gridwidth = 2;
        panel.add(tfsalary, cs);
        //job
        lbjob = new JLabel("Job Type: ");
        cs.gridx = 0;
        cs.gridy = 6;
        cs.gridwidth = 1;
        panel.add(lbjob, cs);
        
        lbjob2 = new JLabel(employee[5]);
        cs.gridx = 1;
        cs.gridy = 6;
        cs.gridwidth = 1;
        panel.add(lbjob2, cs);
        
        tfjob = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 6;
        cs.gridwidth = 2;
        panel.add(tfjob, cs);
 
        
        panel.setBorder(new LineBorder(Color.GRAY));
 
        btnEnter = new JButton("Enter");
 
        btnEnter.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
            	try {
					JOptionPane.showMessageDialog(EditEmployeeDialog.this, "You have successfully entered the information. "
							+ " First Name: " + getFN() + " Last Name: " + getLN() + " SSN: "
									+ getS() + " Salary: " + getH() + " Hourly: " + getSal() + " Job Type: " + getJType());
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} 
            	
            	update[0] = getFN();
            	update[1] = getLN();
            	update[2] = getS();
            	update[3] = getH();
            	update[4] = getSal();
            	update[5] = getJType();
            	
            	try {
					UIController.updateEmployeeInformation(eid, update);
					dispose();
					HRPage.main(null);
				} catch (SQLException e1) {
					e1.printStackTrace();
					EditEmployee.main(null);
				}
            }
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                dispose();
                HRPage.main(null);
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnEnter);
        bp.add(btnCancel);
        
        JButton btnLogout = new JButton("Logout");
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
	
	public String getFN() {
		return tffname.getText().trim();
	}
	
	public String getLN() {
		return tflname.getText().trim();
	}
	
	public String getS() {
		boolean error = true;
    	if(tfssn.getText().trim().length() != 9 || !(tfssn.getText().trim().matches("[0-9]+"))) {
    		if(error) {
    			error = false;
	    		JOptionPane.showMessageDialog(EditEmployeeDialog.this,
			            "Invalid input for Social security number, try again",
			            "Create new employee",
			            JOptionPane.ERROR_MESSAGE);
	    		return null;
    		}
    	}
    	return tfssn.getText().trim();
	}
	
	public String getSal() {
		boolean error = true;
		try {
			return tfsalary.getText().trim();
		}
		catch (Exception e){
			if (error) {
				JOptionPane.showMessageDialog(EditEmployeeDialog.this,
						"Invalid input for pay, try again",
						"Create new employee",
		            	JOptionPane.ERROR_MESSAGE);
				error = false;
			}
			
		}
		return null;
	}
	
	public String getH() {
		return tfhourly.getText().trim();
	}
	
	public String getJType() {
		boolean error = true;
    	String job = tfjob.getText().trim();
    	if(job.equals("hr")||job.equals("human resources")) {
    		job = "hr";
    	}else if (job.equals("engineering")||job.equals("engineer") ) {
    		job = "engineering";
    	}else if (job.equals("sales")||job.equals("sale")) {
    		job = "sales";
    	}else if(job.equals("admin")||job.equals("administrator")) {
    		job = "admin";
    	}else {
    		if(error) {
    			JOptionPane.showMessageDialog(EditEmployeeDialog.this,
    					"Invalid input for Job type, try again",
    					"Create new employee",
    					JOptionPane.ERROR_MESSAGE);
    			error = false;
    			return null;
    		}
    	}
    	return job;
	}
}
