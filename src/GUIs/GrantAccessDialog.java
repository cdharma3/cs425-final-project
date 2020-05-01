package GUIs;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
@SuppressWarnings("unused")
public class GrantAccessDialog extends JDialog {
	//grantAccess(String table, Hashtable<String, Boolean> access, String E_ID)
	private static final long serialVersionUID = 1L;
	private JTextField tfeid;				// Employee ID/ Login Id
	private JLabel lbeid;
	private JTextField tftable;				// Employee ID/ Login Id
	private JLabel lbtable;
	private JLabel lbtables;
	private JCheckBox cbSelect;				// Is Hourly
	private JCheckBox cbInsert;				// Is Hourly
	private JCheckBox cbUpdate;				// Is Hourly
	private JCheckBox cbDelete;				// Is Hourly
	//private JLabel lbhourly;
	private JButton btnLogout;				//Logout
	private JButton btnEnter;				// Enter Button
	private JButton btnCancel;				// Cancel Button
	private static boolean succeeded;				// If successful

	public GrantAccessDialog(Frame parent) {
		super(parent, "Grant Access to an Employee", true);
		//
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();

		cs.fill = GridBagConstraints.HORIZONTAL;

		//employee id
		this.lbeid = new JLabel("Employee ID: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(this.lbeid, cs);

		this.tfeid = new JTextField(50);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(this.tfeid, cs);

		this.lbtables = new JLabel("	Tables available are:  employee,  inventory,  customer,  login,  model,  orderinfo");
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(this.lbtables, cs);

		this.lbtable = new JLabel("Table you want them to have access to: ");
		cs.gridx = 0;
		cs.gridy = 2;
		cs.gridwidth = 1;
		panel.add(this.lbtable, cs);

		this.tftable = new JTextField(50);
		cs.gridx = 1;
		cs.gridy = 2;
		cs.gridwidth = 2;
		panel.add(this.tftable, cs);

		this.cbSelect = new JCheckBox("Select");
		cs.gridx = 0;
		cs.gridy = 3;
		cs.gridwidth = 2;
		panel.add(this.cbSelect, cs);

		this.cbInsert = new JCheckBox("Insert");
		cs.gridx = 0;
		cs.gridy = 4;
		cs.gridwidth = 2;
		panel.add(this.cbInsert, cs);

		this.cbUpdate = new JCheckBox("Update");
		cs.gridx = 0;
		cs.gridy = 5;
		cs.gridwidth = 2;
		panel.add(this.cbUpdate, cs);

		this.cbDelete = new JCheckBox("Delete");
		cs.gridx = 0;
		cs.gridy = 6;
		cs.gridwidth = 2;
		panel.add(this.cbDelete, cs);

		panel.setBorder(new LineBorder(Color.GRAY));

		this.btnEnter = new JButton("Enter");

		this.btnEnter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Hashtable<String, Boolean> access = new Hashtable<String, Boolean>();
					access.put("select", GrantAccessDialog.this.getSelect());
					access.put("insert", GrantAccessDialog.this.getInsert());
					access.put("update", GrantAccessDialog.this.getUpdate());
					access.put("delete", GrantAccessDialog.this.getDelete());

					UIController.grantAccess(GrantAccessDialog.this.getTable(), access, GrantAccessDialog.this.getEid());

				} catch (Exception e1) {
					GrantAccessDialog.this.dispose();
				}

				JOptionPane.showMessageDialog(GrantAccessDialog.this,
						"Employee granted access successfully!",
						"Grant an employee access",
						JOptionPane.INFORMATION_MESSAGE);
				GrantAccessDialog.this.dispose();
				AdminPage.main(null);
			}
		}
				);
		this.btnCancel = new JButton("Cancel");
		this.btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GrantAccessDialog.this.dispose();
				AdminPage.main(null);
			}
		});
		JPanel bp = new JPanel();
		bp.add(this.btnEnter);
		bp.add(this.btnCancel);

		this.btnLogout = new JButton("Logout");
		this.btnLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIController.logout();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				GrantAccessDialog.this.dispose();
			}
		});
		bp.add(this.btnLogout);

		this.getContentPane().add(panel, BorderLayout.CENTER);
		this.getContentPane().add(bp, BorderLayout.PAGE_END);

		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	// * UIController.addEmployee("bWatts", "abc123", "Bob", "Watts", "123456789", (float)25.00, true, "hr");

	public String getEid() {
		return this.tfeid.getText().trim();
	}

	public String getTable() {
		return this.tftable.getText().trim();
	}


	public Boolean getSelect() {
		if(this.cbSelect.isSelected()) {
			return true;
		}
		else {
			return false;
		}
	}

	public Boolean getInsert() {
		if(this.cbInsert.isSelected()) {
			return true;
		}
		else {
			return false;
		}
	}

	public Boolean getUpdate() {
		if(this.cbUpdate.isSelected()) {
			return true;
		}
		else {
			return false;
		}
	}

	public Boolean getDelete() {
		if(this.cbDelete.isSelected()) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isSucceeded() {
		return succeeded;
	}
}