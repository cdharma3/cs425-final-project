package GUIs;
import java.awt.*;

import javax.swing.JDialog;
public class EditEmployeeDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditEmployeeDialog(Frame parent,String eid) {
		super(parent,"Edit Employee",true);
		
		UIController.employeeInfo(eid);
	}
}
