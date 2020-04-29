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

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class BusinessReportsDialog extends JDialog {
	//new vars
	JTextArea textArea;
	
	//old
	private JTextField tfeid;
    private JTextField tfcid;
    private JTextField tfmname;
    private JTextField tfquantity;
    private JLabel lbeid;
    private JLabel lbcid;
    private JLabel lbmname;
    private JLabel lbquantity;
    private JButton btnEnter;
    private JButton btnCancel;
    
    public BusinessReportsDialog() {}
    public BusinessReportsDialog(Frame parent) {
        super(parent, "Business Report", true);
        JPanel middlePanel = new JPanel ();
        middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Display Area" ) );

        // create the middle panel components

        JTextArea display = new JTextArea ( 16, 58 );
        display.setEditable ( false ); // set textArea non-editable
        JScrollPane scroll = new JScrollPane ( display );
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

        //Add Textarea in to middle panel
        middlePanel.add ( scroll );

        // My code
        parent.add ( middlePanel );
        parent.pack ();
        parent.setLocationRelativeTo ( null );
        parent.setVisible ( true );
        
        
        //business reports code
        /*textArea = new JTextArea("Hello, my name is Adam");        
        JScrollPane scrollPane = new JScrollPane(textArea); 
        textArea.setEditable(true);
        textArea.append("Hello, my name is Adam");*/
        
        
        //old
       /* JPanel panel = new JPanel(new GridBagLayout());
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
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnEnter);
        bp.add(btnCancel);
 
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);*/
    }
}
