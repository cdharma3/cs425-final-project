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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
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
		this.lbModel = new JLabel("Model Name: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(this.lbModel, cs);

		this.tfModel = new JTextField(50);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(this.tfModel, cs);

		//First Name
		this.lbsaleprice = new JLabel("Price: ");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(this.lbsaleprice, cs);

		this.tfsaleprice = new JTextField(50);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(this.tfsaleprice, cs);

		panel.setBorder(new LineBorder(Color.GRAY));

		this.btnEnter = new JButton("Enter");

		this.btnEnter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(CreateModelDialog.this.getPrice() != null) {
						UIController.addModel(CreateModelDialog.this.getModel(), CreateModelDialog.this.getPrice());
						JOptionPane.showMessageDialog(CreateModelDialog.this,
								"Model created successfully!",
								"Create new Model",
								JOptionPane.INFORMATION_MESSAGE);
					}else {
						CreateModelDialog.this.dispose();
						MainCreateModel.main(null);
					}
				} catch (Exception e1) {
					System.err.println(e1.toString());
					JOptionPane.showMessageDialog(CreateModelDialog.this,
							"Error creating model, please try again",
							"Create new model",
							JOptionPane.ERROR_MESSAGE);
				}

				CreateModelDialog.this.dispose();
				if(CreateModelDialog.this.getPrice() != null) {
					EngPage.main(null);
				}
			}
		});
		this.btnCancel = new JButton("Cancel");
		this.btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CreateModelDialog.this.dispose();
				EngPage.main(null);
			}
		});
		this.btnLogout = new JButton("Logout");
		this.btnLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIController.logout();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				CreateModelDialog.this.dispose();
			}
		});

		JPanel bp = new JPanel();
		bp.add(this.btnLogout);
		bp.add(this.btnEnter);
		bp.add(this.btnCancel);

		this.getContentPane().add(panel, BorderLayout.CENTER);
		this.getContentPane().add(bp, BorderLayout.PAGE_END);

		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	// * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");

	public String getModel() {
		return this.tfModel.getText().trim();
	}

	public Float getPrice() {
		//checking to see if its a float
		try {
			return Float.parseFloat(this.tfsaleprice.getText().trim());
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