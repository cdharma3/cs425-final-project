package GUIs;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;
@SuppressWarnings("unused")
public class EngMainPage extends JDialog {
	 
	    /**
	 * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");
	 */
	private static final long serialVersionUID = 1L;
		private JButton btnCreate;				//Create new models
	    private JButton btnModel;				// Access and update Model
	    private JButton btnInventory;			// Access and update Inventory
	    private JButton btnEmpl;				// Access (limited) to employee info
	    private JButton btnLogout;				//Logout
	    private boolean succeeded;				// If successful
	 
	    public EngMainPage(Frame parent) {
	        super(parent, "Engineer Main Page", true);
	        //
	        JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints cs = new GridBagConstraints();
	 
	        cs.fill = GridBagConstraints.HORIZONTAL;
	        
	        //employee id
	       
	        //Line separating buttons and welcome message
	        //Create new employee button and label
	        btnCreate = new JButton("Create a new Model entry");
	        btnCreate.addActionListener(new ActionListener() {
	 
	        	public void actionPerformed(ActionEvent e) {
	                dispose();
	                MainCreateModel.main(null);
	            }
	        });
	        panel.add(btnCreate);
	        
	        btnModel = new JButton("Access and update Model");
	        btnModel.addActionListener(new ActionListener() {
	 
	        	public void actionPerformed(ActionEvent e) {
	        		dispose();
	                MainCreateEmployee.main(null);
	            }
	        });
	        panel.add(btnModel);
	        
	        btnInventory = new JButton("Access and update Inventory");
	        btnInventory.addActionListener(new ActionListener() {
	        
	            public void actionPerformed(ActionEvent e) {
	            	//TODO
	                dispose();
	            }
	        });
	        panel.add(btnInventory);
	        
	        btnEmpl = new JButton("Access Employee Info.");
	        btnEmpl.addActionListener(new ActionListener() {
	        
	            public void actionPerformed(ActionEvent e) {
	            	//TODO
	                dispose();
	            }
	        });
	        panel.add(btnEmpl);
	        
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
	        panel.add(btnLogout);
	        
	 
	        getContentPane().add(panel, BorderLayout.CENTER);
	        getContentPane().add(panel, BorderLayout.PAGE_END);
	 
	        pack();
	        setResizable(false);
	        setLocationRelativeTo(parent);
	        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    }
		// * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");


	    public boolean isSucceeded() {
	        return succeeded;
	    }
	}