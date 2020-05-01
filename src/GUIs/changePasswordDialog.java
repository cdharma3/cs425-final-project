package GUIs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class changePasswordDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lbeid, lbfname;
	JLabel lbeid2;
	JTextField tffname;
	JButton btnEnter, btnCancel;
	
	public changePasswordDialog(Frame parent,String eid) throws SQLException {
		super(parent,"Edit Password",true);
		String [] employee;
		boolean pass = true;
		employee = UIController.displayEmployeeInformation(eid);
		try {
			if(employee[0] == null) {
				
			}
		}catch(Exception e2){
			JOptionPane.showMessageDialog(changePasswordDialog.this,
		            "Invalid input for Employee ID, try again",
		            "Edit Password",
		            JOptionPane.ERROR_MESSAGE);
			dispose();
			pass = false;
			changePassword.main(null);
			
		}
		if(pass) {
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
	        lbfname = new JLabel("Enter New Password: ");
	        cs.gridx = 0;
	        cs.gridy = 1;
	        cs.gridwidth = 1;
	        panel.add(lbfname, cs);
	        
	        tffname = new JTextField(20);
	        cs.gridx = 2;
	        cs.gridy = 1;
	        cs.gridwidth = 2;
	        panel.add(tffname, cs);
	       
	 
	        
	        panel.setBorder(new LineBorder(Color.GRAY));
	 
	        btnEnter = new JButton("Enter");
	 
	        btnEnter.addActionListener(new ActionListener() {
	 
	            public void actionPerformed(ActionEvent e) {
	            	try {
						JOptionPane.showMessageDialog(changePasswordDialog.this, "You have successfully entered the information. ");
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} 
	            	
	            	try {
						UIController.updatePassword(getFN(),eid);
						dispose();
						AdminPage.main(null);
					} catch (SQLException e1) {
						e1.printStackTrace();
						changePassword.main(null);
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidKeySpecException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        });
	        btnCancel = new JButton("Cancel");
	        btnCancel.addActionListener(new ActionListener() {
	 
	            public void actionPerformed(ActionEvent e) {
	                dispose();
	                AdminPage.main(null);
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
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			
		}
	}
	
	public String getFN() {
		return tffname.getText().trim();
	}
}
