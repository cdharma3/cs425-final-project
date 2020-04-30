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
	
	
	public EditEmployeeDialog(Frame parent,String eid) throws SQLException {
		super(parent,"Edit Employee",true);
		String [] employee;
		
			employee = UIController.displayEmployeeInformation(eid);
		
		
		JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
        //employee id
        lbeid = new JLabel("Employee ID: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbeid, cs);
        
        
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
        //salary
        lbsalary = new JLabel("Salary: ");
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(lbsalary, cs);
        
        lbsalary2 = new JLabel(employee[3]);
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(lbsalary2, cs);
        
        tfsalary = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 4;
        cs.gridwidth = 2;
        panel.add(tfsalary, cs);
        //hourly
        lbhourly = new JLabel("Hourly: ");
        cs.gridx = 0;
        cs.gridy = 5;
        cs.gridwidth = 1;
        panel.add(lbhourly, cs);
        
        lbhourly2 = new JLabel(employee[4]);
        cs.gridx = 1;
        cs.gridy = 5;
        cs.gridwidth = 1;
        panel.add(lbhourly2, cs);
        
        tfhourly = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 5;
        cs.gridwidth = 2;
        panel.add(tfhourly, cs);
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
 
        /*btnEnter = new JButton("Enter");
 
 		UPDATEEMPLOYYEINFORMATION method needed
 
        btnEnter.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
            	try {
					JOptionPane.showMessageDialog(OrderFormDialog.this, "You have successfully entered the information. "
							+ " Employee ID: " + getEid() + " Customer ID: " + getCid() + " Model Name: "
									+ getMname() + " Quantity: " + getQ() + " Sale Value: " + calcSaleValue(getQ()));
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            	try {
					if(UIController.getInventoryQuantity(getMname())>getQ()) {
						try {
							enterOrder();
							dispose();
							SalesPage.main(null);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else {
						JOptionPane.showMessageDialog(OrderFormDialog.this, "The quantity you have ordered is not available, "
								+ "please note that the quantity available is: " + UIController.getInventoryQuantity(getMname()));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
            }
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                dispose();
                SalesPage.main(null);
            }
        });*/
       /* JPanel bp = new JPanel();
        //bp.add(btnEnter);
        //bp.add(btnCancel);
        
        JButton btnLogout = new JButton("Logout");
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
		bp.add(btnLogout);
 
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);*/
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
	}
}
