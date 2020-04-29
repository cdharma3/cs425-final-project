package GUIs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class BusinessReports {
	public static void main(String[] args) {
        final JFrame frame = new JFrame("Business Report");
        final JButton btnRev = new JButton("Generate Report");
        btnRev.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        BusinessReportsDialog bRep = new BusinessReportsDialog(frame);
                        bRep.setVisible(true);
                        
                    }
                });
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnRev);
        frame.setVisible(true);
	}
}
