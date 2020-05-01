package GUIs;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.border.*;
@SuppressWarnings("unused")
public class GrantAccessDialog extends JDialog {
	//grantAccess(String table, Hashtable<String, Boolean> access, String E_ID)
	private static final long serialVersionUID = 1L;
		private JTextField tfeid;				// Employee ID/ Login Id
		private JLabel lbeid;
		private JTextField tftable;				// Employee ID/ Login Id
		private JLabel lbtable;
		private JLabel lbtables;
	    private JCheckBox cbSelect;				// Is Hourly
	    private JCheckBox cbInsert;				// Is Hourly
	    private JCheckBox cbUpdate;				// Is Hourly
	    private JCheckBox cbDelete;				// Is Hourly
	    //private JLabel lbhourly;
	    private JButton btnLogout;				//Logout
	    private JButton btnEnter;				// Enter Button
	    private JButton btnCancel;				// Cancel Button
	    private static boolean succeeded;				// If successful
	 
	    public GrantAccessDialog(Frame parent) {
	        super(parent, "Grant Access to an Employee", true);
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
	        
	        lbtables = new JLabel("	Tables available are:  employee,  inventory,  customer,  login,  model,  orderinfo");
	        cs.gridx = 1;
	        cs.gridy = 1;
	        cs.gridwidth = 1;
	        panel.add(lbtables, cs);
	        
	        lbtable = new JLabel("Table you want them to have access to: ");
	        cs.gridx = 0;
	        cs.gridy = 2;
	        cs.gridwidth = 1;
	        panel.add(lbtable, cs);
	        
	        tftable = new JTextField(50);
	        cs.gridx = 1;
	        cs.gridy = 2;
	        cs.gridwidth = 2;
	        panel.add(tftable, cs);
	        
	        cbSelect = new JCheckBox("Select");
	        cs.gridx = 0;
	        cs.gridy = 3;
	        cs.gridwidth = 2;
	        panel.add(cbSelect, cs);
	        
	        cbInsert = new JCheckBox("Insert");
	        cs.gridx = 0;
	        cs.gridy = 4;
	        cs.gridwidth = 2;
	        panel.add(cbInsert, cs);
	        
	        cbUpdate = new JCheckBox("Update");
	        cs.gridx = 0;
	        cs.gridy = 5;
	        cs.gridwidth = 2;
	        panel.add(cbUpdate, cs);
	        
	        cbDelete = new JCheckBox("Delete");
	        cs.gridx = 0;
	        cs.gridy = 6;
	        cs.gridwidth = 2;
	        panel.add(cbDelete, cs);
	        
	        panel.setBorder(new LineBorder(Color.GRAY));
	 
	        btnEnter = new JButton("Enter");
	 
	        btnEnter.addActionListener(new ActionListener() {
	 
	        	public void actionPerformed(ActionEvent e) {
	                try {
	                	
	                	//UIController.grantAccess(getTable(), Hashtable<String, Boolean> access, getEid());
	                	
					} catch (Exception e1) {
						dispose();
					}
	                
	                JOptionPane.showMessageDialog(GrantAccessDialog.this,
				            "Employee granted access successfully!",
				            "Grant an employee access",
				            JOptionPane.INFORMATION_MESSAGE);
	                dispose();
	                AdminPage.main(null);
	                }
	            }
	        );
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

	    public String getEid() {
	        return tfeid.getText().trim();
	    }
	    
	    public String getTable() {
	        return tftable.getText().trim();
	    }
	 

	    public Boolean getSelect() {
			if(cbSelect.isSelected()) {
				return true;
			}
			else {
				return false;
			}
	    }
	    
	    public Boolean getInsert() {
			if(cbInsert.isSelected()) {
				return true;
			}
			else {
				return false;
			}
	    }
	    
	    public Boolean getUpdate() {
			if(cbUpdate.isSelected()) {
				return true;
			}
			else {
				return false;
			}
	    }
	    
	    public Boolean getDelete() {
			if(cbDelete.isSelected()) {
				return true;
			}
			else {
				return false;
			}
	    }
	    
	    public static boolean isSucceeded() {
	        return succeeded;
	    }
	}