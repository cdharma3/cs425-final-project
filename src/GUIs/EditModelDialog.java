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

public class EditModelDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lbname, lbnum, lbpc, lbsp;
	JLabel lbname2, lbnum2, lbpc2, lbsp2;
	JTextField tfnum, tfpc, tfsp;
	JButton btnEnter, btnCancel;
	
	public EditModelDialog(Frame parent,String modelName) throws SQLException {
		super(parent,"Edit Model",true);
		String [] customer;
		String [] update = new String[3];
		customer = UIController.displayModelInformation(modelName);
		
		
		JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
        //model name
        lbname = new JLabel("Model Name: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbname, cs);
        
        lbname2 = new JLabel(modelName);
        cs.gridx = 2;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbname2, cs);
        
        
        //model number
        lbnum = new JLabel("Model Number: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbnum, cs);
        
        lbnum2 = new JLabel(customer[0]);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbnum2, cs);
        
        tfnum = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(tfnum, cs);
        //production cost
        lbpc = new JLabel("Production Cost: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbpc, cs);
        
        lbpc2 = new JLabel(customer[1]);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbpc2, cs);
        
        tfpc = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(tfpc, cs);
        //sale price
        lbsp = new JLabel("Sale Price: ");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbsp, cs);
        
        lbsp2 = new JLabel(customer[2]);
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbsp2, cs);
        
        tfsp = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 3;
        cs.gridwidth = 2;
        panel.add(tfsp, cs);
        
 
        
        panel.setBorder(new LineBorder(Color.GRAY));
 
        btnEnter = new JButton("Enter");
 
        btnEnter.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
            	try {
					JOptionPane.showMessageDialog(EditModelDialog.this, "You have successfully entered the information. "
							+ " Model Number: " + getNUM() + " Production Cost: " + getPC() + " Sale Price: " + getSP());
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} 
            	
            	update[0] = getNUM();
            	update[1] = getPC();
            	update[2] = getSP();
            	
            	try {
					UIController.updateModelInformation(modelName, update);
					dispose();
					EngPage.main(null);
				} catch (SQLException e1) {
					e1.printStackTrace();
					dispose();
					EditModel.main(null);
				}
            }
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                dispose();
                EngPage.main(null);
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
	
	public String getNUM() {
		return tfnum.getText().trim();
	}
	
	public String getPC() {
		return tfpc.getText().trim();
	}
	
	public String getSP() {
		return tfsp.getText().trim();
	}
}
