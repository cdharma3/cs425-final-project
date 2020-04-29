package GUIs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainOrderForm {
	public static void main(String[] args) {
        final JFrame frame = new JFrame("Order Form");
        final JButton btnOrder = new JButton("Click to Begin");
        btnOrder.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        OrderFormDialog OrderDlg = new OrderFormDialog(frame);
                        OrderDlg.setVisible(true);
                        
                    }
                });
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnOrder);
        frame.setVisible(true);
    }
}
