package GUIs;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;
public class OrderFormDialog extends JDialog {
	 
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private JTextField tfeid;
	    private JTextField tfcid;
	    private JTextField tfmname;
	    private JTextField tfquantity;
	    private JLabel lbeid;
	    private JLabel lbcid;
	    private JLabel lbmname;
	    private JLabel lbquantity;
	    private JButton btnEnter;
	    private JButton btnLogout;				//Logout
	    private JButton btnCancel;

	    public OrderFormDialog(Frame parent) {
	        super(parent, "Order", true);
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
	        
	        tfeid = new JTextField(20);
	        cs.gridx = 1;
	        cs.gridy = 0;
	        cs.gridwidth = 2;
	        panel.add(tfeid, cs);
	        //customer id
	        lbcid = new JLabel("Customer ID: ");
	        cs.gridx = 0;
	        cs.gridy = 1;
	        cs.gridwidth = 1;
	        panel.add(lbcid, cs);
	        
	        tfcid = new JTextField(20);
	        cs.gridx = 1;
	        cs.gridy = 1;
	        cs.gridwidth = 2;
	        panel.add(tfcid, cs);
	        //model number
	        lbmname = new JLabel("Model Name: ");
	        cs.gridx = 0;
	        cs.gridy = 2;
	        cs.gridwidth = 1;
	        panel.add(lbmname, cs);
	        
	        tfmname = new JTextField(20);
	        cs.gridx = 1;
	        cs.gridy = 2;
	        cs.gridwidth = 2;
	        panel.add(tfmname, cs);
	        //quantity
	        lbquantity = new JLabel("Quantity: ");
	        cs.gridx = 0;
	        cs.gridy = 3;
	        cs.gridwidth = 1;
	        panel.add(lbquantity, cs);
	        
	        tfquantity = new JTextField(20);
	        cs.gridx = 1;
	        cs.gridy = 3;
	        cs.gridwidth = 2;
	        panel.add(tfquantity, cs);
	 
	        
	        panel.setBorder(new LineBorder(Color.GRAY));
	 
	        btnEnter = new JButton("Enter");
	 
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
	    
	    //eid
	    public String getEid() {
	    	return tfeid.getText().trim();
	    }
	    //cid
	    public String getCid() {
	    	return tfcid.getText().trim();
	    }
	    //model name
	    public String getMname() {
	    	return tfmname.getText().trim();
	    }
	    //quantity
	    public int getQ() {
	    	String s = tfquantity.getText().trim();
	    	int t = Integer.parseInt(s);
	    	return t;
	    }
	    //salevalue
	    public double calcSaleValue(int quantity) throws SQLException {
	    	double saleValue = quantity*UIController.getSalePrice(getMname());
	    	return saleValue;
	    }
	    
	    public void enterOrder() throws SQLException {
	    	UIController.addOrder(getCid(), getEid(), getMname(), getQ());
	    }
	    
}
