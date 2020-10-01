package sprint1.ui;

import javax.swing.JDialog;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SocioWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MainWindow parent = null;
	private JButton btnReturn;

	/**
	 * Create the dialog.
	 */
	public SocioWindow(MainWindow mainWindow) {
		setTitle("Accediendo como socio...");
		this.parent = mainWindow;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		getContentPane().add(getBtnReturn());
	}

	private JButton getBtnReturn() {
		if (btnReturn == null) {
			btnReturn = new JButton("Return");
			btnReturn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnReturn;
	}
}
