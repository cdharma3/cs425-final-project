package GUIs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainCreateModel {
	public static void main(String[] args) {
        final JFrame frame = new JFrame("Create a new Model");
        final JButton btnOrder = new JButton("Click to Begin");
 
        btnOrder.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                    	frame.dispose();
                    	CreateModelDialog OrderDlg = new CreateModelDialog(frame);
                        OrderDlg.setVisible(true);
                        return;
                    }
                });
 
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(400, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnOrder);
        frame.setVisible(true);
    }
}
