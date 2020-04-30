package GUIs;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;
@SuppressWarnings("unused")
public class CreateModelDialog extends JDialog {
	 
	private static final long serialVersionUID = 1L;
		private JTextField tfModel;				// Model Name
		private JLabel lbModel;
	    private JTextField tfsaleprice;				// First Name
	    private JLabel lbsaleprice;
	    private JButton btnEnter;				// Enter Button
	    private JButton btnCancel;				// Cancel Button
	    private JButton btnLogout;				//Logout
	    private boolean succeeded;				// If successful
	 
	    public CreateModelDialog(Frame parent) {
	        super(parent, "New Model", true);
	        //
	        JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints cs = new GridBagConstraints();
	 
	        cs.fill = GridBagConstraints.HORIZONTAL;
	        
	        //employee id
	        lbModel = new JLabel("Model Name: ");
	        cs.gridx = 0;
	        cs.gridy = 0;
	        cs.gridwidth = 1;
	        panel.add(lbModel, cs);
	        
	        tfModel = new JTextField(50);
	        cs.gridx = 1;
	        cs.gridy = 0;
	        cs.gridwidth = 2;
	        panel.add(tfModel, cs);
	        
	        //First Name
	        lbsaleprice = new JLabel("Price: ");
	        cs.gridx = 0;
	        cs.gridy = 1;
	        cs.gridwidth = 1;
	        panel.add(lbsaleprice, cs);
	        
	        tfsaleprice = new JTextField(50);
	        cs.gridx = 1;
	        cs.gridy = 1;
	        cs.gridwidth = 2;
	        panel.add(tfsaleprice, cs);
	        
	        panel.setBorder(new LineBorder(Color.GRAY));
	 
	        btnEnter = new JButton("Enter");
	 
	        btnEnter.addActionListener(new ActionListener() {
	 
	        	public void actionPerformed(ActionEvent e) {
	                try {
	                	if(getPrice() != null) {
		                	UIController.addModel(getModel(), getPrice());
		                	JOptionPane.showMessageDialog(CreateModelDialog.this,
						            "Model created successfully!",
						            "Create new Model",
						            JOptionPane.INFORMATION_MESSAGE);
	                	}else {
	                		dispose();
							MainCreateModel.main(null);
	                	}
					} catch (Exception e1) {
						System.err.println(e1.toString());
						JOptionPane.showMessageDialog(CreateModelDialog.this,
					            "Error creating model, please try again",
					            "Create new model",
					            JOptionPane.ERROR_MESSAGE);
					}
	                
	                dispose();
	                if(getPrice() != null) {
	                	EngPage.main(null);
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
	        
	        JPanel bp = new JPanel();
	        bp.add(btnLogout);
	        bp.add(btnEnter);
	        bp.add(btnCancel);
	 
	        getContentPane().add(panel, BorderLayout.CENTER);
	        getContentPane().add(bp, BorderLayout.PAGE_END);
	 
	        pack();
	        setResizable(false);
	        setLocationRelativeTo(parent);
	        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    }
		// * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");

	    public String getModel() {
	        return tfModel.getText().trim();
	    }
	 
	    public Float getPrice() {
	    	//checking to see if its a float
			try {
				return Float.parseFloat(tfsaleprice.getText().trim());
			}
			catch (Exception e){
				JOptionPane.showMessageDialog(CreateModelDialog.this,
			            "Invalid input for pay, try again",
			            "Create new Model",
			            JOptionPane.ERROR_MESSAGE);
				
			}
			return null;
	    }
	}