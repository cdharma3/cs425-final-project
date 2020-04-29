package GUIs;
import java.awt.*;
import java.awt.event.*;
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
	    private JLabel lbsale;
	    private JButton btnEnter;
	    private JButton btnCancel;
	    private boolean succeeded;
	 
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
	            	JOptionPane.showMessageDialog(OrderFormDialog.this, "You have successfully entered the information. "
	            			+ "Please wait a moment as the sale value is calculated");
	        
	            	JPanel form = new JPanel(new GridBagLayout());
	    	        GridBagConstraints cs2 = new GridBagConstraints();
	    	 
	    	        cs2.fill = GridBagConstraints.HORIZONTAL;
	    	        //employee id
	    	        lbeid = new JLabel("Employee ID: ");
	    	        cs2.gridx = 0;
	    	        cs2.gridy = 0;
	    	        cs2.gridwidth = 1;
	    	        form.add(lbeid, cs2);
	    	        
	    	        JLabel lbpeid;
	    	        
	    	        lbpeid = new JLabel(getEid());
	    	        cs2.gridx = 1;
	    	        cs2.gridy = 0;
	    	        cs2.gridwidth = 2;
	    	        form.add(lbpeid, cs2);
	    	        //customer id
	    	        lbcid = new JLabel("Customer ID: ");
	    	        cs2.gridx = 0;
	    	        cs2.gridy = 1;
	    	        cs2.gridwidth = 1;
	    	        form.add(lbcid, cs2);
	    	        
	    	        JLabel lbpcid;
	    	        
	    	        lbpcid = new JLabel(getCid());
	    	        cs2.gridx = 1;
	    	        cs2.gridy = 1;
	    	        cs2.gridwidth = 2;
	    	        form.add(lbpcid, cs2);
	    	        //model number
	    	        lbmname = new JLabel("Model Name: ");
	    	        cs2.gridx = 0;
	    	        cs2.gridy = 2;
	    	        cs2.gridwidth = 1;
	    	        form.add(lbmname, cs2);
	    	        
	    	        JLabel lbpmname;
	    	        lbpmname = new JLabel(getMname());
	    	        cs2.gridx = 1;
	    	        cs2.gridy = 2;
	    	        cs2.gridwidth = 2;
	    	        form.add(lbpmname, cs2);
	    	        //quantity
	    	        lbquantity = new JLabel("Customer ID: ");
	    	        cs2.gridx = 0;
	    	        cs2.gridy = 3;
	    	        cs2.gridwidth = 1;
	    	        form.add(lbquantity, cs2);
	    	        
	    	        JLabel lbpquantity;
	    	        
	    	        lbpquantity = new JLabel(String.valueOf(getQ()));
	    	        cs2.gridx = 1;
	    	        cs2.gridy = 3;
	    	        cs2.gridwidth = 2;
	    	        form.add(lbpquantity, cs2);
	    	        
	    	        //sale value
	    	        lbsale = new JLabel("Sale Value: ");
	    	        cs2.gridx = 0;
	    	        cs2.gridy = 4;
	    	        form.add(lbsale, cs2);
	    	        
	    	        JLabel lbpsale;
	    	        
	    	        lbpsale = new JLabel(String.valueOf(calcSaleValue(getQ())));
	    	        cs2.gridx = 1;
	    	        cs2.gridy = 4;
	    	        form.add(lbpsale, cs2);
	    	        
	    	        panel.setBorder(new LineBorder(Color.GRAY));
	            }
	        });
	        btnCancel = new JButton("Cancel");
	        btnCancel.addActionListener(new ActionListener() {
	 
	            public void actionPerformed(ActionEvent e) {
	                dispose();
	            }
	        });
	        JPanel bp = new JPanel();
	        bp.add(btnEnter);
	        bp.add(btnCancel);
	 
	        getContentPane().add(panel, BorderLayout.CENTER);
	        getContentPane().add(bp, BorderLayout.PAGE_END);
	 
	        pack();
	        setResizable(false);
	        setLocationRelativeTo(parent);
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
	    public double calcSaleValue(int quantity) {
	    	return 33.75;
	    	
	    }
	 
	    public boolean isSucceeded() {
	        return succeeded;
	    }
	}
