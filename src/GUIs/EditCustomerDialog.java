package GUIs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
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

public class EditCustomerDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lbeid, lbfname, lblname;
	JLabel lbeid2, lbfname2, lblname2;
	JTextField tffname, tflname;
	JButton btnEnter, btnCancel;
	
	public EditCustomerDialog(Frame parent,String cid) throws SQLException {
		super(parent,"Edit Customer",true);
		String [] customer;
		String [] update = new String[2];
		customer = UIController.displayCustomerInformation(cid);
		try {
			if(customer[0] == null) {
				
			}
		}catch(Exception e2){
			JOptionPane.showMessageDialog(EditCustomerDialog.this,
		            "Invalid input for Employee ID, try again",
		            "Edit Employee",
		            JOptionPane.ERROR_MESSAGE);
			dispose();
			EditEmployee.main(null);
		}
		
		JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
        //customer id
        lbeid = new JLabel("Customer ID: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbeid, cs);
        
        lbeid2 = new JLabel(cid);
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
        
        lbfname2 = new JLabel(customer[0]);
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
        
        lblname2 = new JLabel(customer[1]);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lblname2, cs);
        
        tflname = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(tflname, cs);
        
 
        
        panel.setBorder(new LineBorder(Color.GRAY));
 
        btnEnter = new JButton("Enter");
 
        btnEnter.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
            	try {
					JOptionPane.showMessageDialog(EditCustomerDialog.this, "You have successfully entered the information. "
							+ " First Name: " + getFN() + " Last Name: " + getLN());
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} 
            	
            	update[0] = getFN();
            	update[1] = getLN();
            	
            	try {
					UIController.updateEmployeeInformation(cid, update);
					dispose();
					SalesPage.main(null);
				} catch (SQLException e1) {
					e1.printStackTrace();
					EditCustomer.main(null);
				}
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
}
