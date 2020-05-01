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

public class EditInventoryDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lbiid, lbnam, lbpc, lbsp, lblt, lbct, lbq;
	JLabel lbiid2, lbnam2, lbpc2, lbsp2, lblt2, lbct2, lbq2;
	JTextField tfnam, tfpc, tfsp, tflt, tfct, tfq;
	JButton btnEnter, btnCancel;
	
	public EditInventoryDialog(Frame parent,String iid) throws SQLException {
		super(parent,"Edit Inventory",true);
		String [] customer;
		String [] update = new String[5];
		customer = UIController.displayInventoryInformation(iid);
		
		JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
        //iid
        lbiid = new JLabel("I_ID: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbiid, cs);
        
        lbiid2 = new JLabel(iid);
        cs.gridx = 2;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbiid2, cs);
        
        
        //model name
        lbnam = new JLabel("Model Name: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbnam, cs);
        
        lbnam2 = new JLabel(customer[0]);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbnam2, cs);
        
        tfnam = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(tfnam, cs);
        //cost
        lbpc = new JLabel("Cost: ");
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
        //lead_time
        lblt = new JLabel("Lead Time: ");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lblt, cs);
        
        lbsp2 = new JLabel(customer[2]);
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbsp2, cs);
        
        tflt = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 3;
        cs.gridwidth = 2;
        panel.add(tflt, cs);
      //category_type
        lbct = new JLabel("Category Type: ");
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(lbct, cs);
        
        lbct2 = new JLabel(customer[3]);
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(lbct2, cs);
        
        tfct = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 4;
        cs.gridwidth = 2;
        panel.add(tfct, cs);
      //quantity
        lbq = new JLabel("Quantity: ");
        cs.gridx = 0;
        cs.gridy = 5;
        cs.gridwidth = 1;
        panel.add(lbq, cs);
        
        lbq2 = new JLabel(customer[4]);
        cs.gridx = 1;
        cs.gridy = 5;
        cs.gridwidth = 1;
        panel.add(lbq2, cs);
        
        tfq = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 5;
        cs.gridwidth = 2;
        panel.add(tfq, cs);
        
 
        
        panel.setBorder(new LineBorder(Color.GRAY));
 
        btnEnter = new JButton("Enter");
 
        btnEnter.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
            	try {
					JOptionPane.showMessageDialog(EditInventoryDialog.this, "You have successfully entered the information. "
							+ " Model Name: " + getNAM() + " Cost: " + getPC() + " Lead Time: " + getLT() + " Category Type: " + getCT() + " Quantity: " + getQ());
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} 
            	
            	update[0] = getNAM();
            	update[1] = getPC();
            	update[2] = getLT();
            	update[3] = getCT();
            	update[4] = getQ();
            	
            	try {
					UIController.updateInventoryInformation(iid, update);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }

			private String getNAM() {
				// TODO Auto-generated method stub
				return null;
			}

			private String getCT() {
				// TODO Auto-generated method stub
				return null;
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
	
	public String getNAM() {
		return tfnam.getText().trim();
	}
	
	public String getPC() {
		return tfpc.getText().trim();
	}
	
	public String getLT() {
		return tfsp.getText().trim();
	}
	
	public String getCT() {
		return tfct.getText().trim();
	}
	
	public String getQ() {
		return tfq.getText().trim();
	}
}
