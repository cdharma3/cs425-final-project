package GUIs;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;
@SuppressWarnings("unused")
public class DeleteEmployeeDialog extends JDialog {
	 
	    /**
	 * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");
	 */
	private static final long serialVersionUID = 1L;
		private JTextField tfeid;						// Employee ID/ Login Id
		private JLabel lbeid;
	    private JButton btnLogout;						//Logout
	    private JButton btnEnter;						// Enter Button
	    private JButton btnCancel;						// Cancel Button
	    private static boolean succeeded;				// If successful
	 
	    public DeleteEmployeeDialog(Frame parent) {
	        super(parent, "Delete Employee", true);
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
	        
	        
	        panel.setBorder(new LineBorder(Color.GRAY));
	 
	        btnEnter = new JButton("Enter");
	 
	        btnEnter.addActionListener(new ActionListener() {
	 
	        	public void actionPerformed(ActionEvent e) {
	                try {
	                	UIController.deleteEmployeeInformation(getEid());
					} catch (Exception e1) {
						dispose();
						MainDeleteEmployee.main(null);
					}
	                JOptionPane.showMessageDialog(DeleteEmployeeDialog.this,
				            "Employee Deleted successfully!",
				            "Delete Employee",
				            JOptionPane.INFORMATION_MESSAGE);
	                dispose();
	                AdminPage.main(null);
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
	 
	        btnLogout = new JButton("Logout");
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
	 
	    public static boolean isSucceeded() {
	        return succeeded;
	    }
	}