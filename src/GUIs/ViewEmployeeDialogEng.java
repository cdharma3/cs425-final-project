package GUIs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

//first name last name position
public class ViewEmployeeDialogEng extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lbeid, lbfname, lblname, lbssn, lbsalary, lbhourly, lbjob;
	JLabel lbeid2, lbfname2, lblname2, lbssn2, lbsalary2, lbhourly2, lbjob2;
	JButton btnCancel;
	
	public ViewEmployeeDialogEng(Frame parent,String eid) throws SQLException {
		super(parent,"View Employee",true);
		String [] employee;
		employee = UIController.engineerAccess(eid);
		
		
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
        //job
        lbjob = new JLabel("Job Type: ");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbjob, cs);
        
        lbjob2 = new JLabel(employee[2]);
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbjob2, cs);
 
        
        panel.setBorder(new LineBorder(Color.GRAY));
 
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                dispose();
                SalesPage.main(null);
            }
        });
        JPanel bp = new JPanel();
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
}
