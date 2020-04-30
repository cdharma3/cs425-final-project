package GUIs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SalesPage {
	public static void main(String[] args) {
        final JFrame frame = new JFrame("Welcome to the Sales Homepage");
        final JButton btnOrder = new JButton("Click to Enter");
 
        btnOrder.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                    	frame.dispose();
                        SalesMainPage OrderDlg = new SalesMainPage(frame);
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
