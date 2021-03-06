package GUIs;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class changePassword {
public static void main(String[] args) {
		
        JFrame frame = new JFrame("Edit Password");
        JPanel panel = new JPanel();
        frame.getContentPane();
        JLabel label = new JLabel("Employee ID: ");
        JTextField enter = new JTextField(20);
        Dimension size = label.getPreferredSize();
        label.setBounds(150, 100, size.width, size.height);
        panel.setLayout(new FlowLayout());
        panel.add(label);
        panel.add(enter);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(300, 300);
        frame.setVisible(true);
        
        
        JButton btnOrder = new JButton("Click to Change Password");
	    JButton btnCancel = new JButton("Cancel");
        btnOrder.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                    	frame.dispose();
                        changePasswordDialog OrderDlg;
						try {
							OrderDlg = new changePasswordDialog(frame,enter.getText().trim());
							OrderDlg.setVisible(true);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
                        
                        
                    }
                });
        btnCancel.addActionListener(new ActionListener() {
       	 
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                HRPage.main(null);
            }
        });
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(400, 150);
        frame.setLayout(new FlowLayout());
        frame.add(btnOrder);
        frame.add(btnCancel);
        frame.setVisible(true);
	}
}
