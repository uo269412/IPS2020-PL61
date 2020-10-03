package sprint1.ui;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminWindow extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow parent = null;
	private JButton btnReturn;
	private JButton btnAsignarMonitor;

	/**
	 * Create the dialog.
	 */
	public AdminWindow(MainWindow mainWindow) {
		setTitle("Accediendo como administrador...");
		this.parent = mainWindow;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		getContentPane().add(getBtnAsignarMonitor());
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

	//wip
	private JButton getBtnAsignarMonitor() {
		if (btnAsignarMonitor == null) {
			btnAsignarMonitor = new JButton("Asignar monitor a actividad");
			btnAsignarMonitor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String codigoActividad;
					do {
						codigoActividad = JOptionPane
								.showInputDialog("Por favor, introduce una id de actividad válida ");
						System.out.println(codigoActividad);
					} while (parent.getPrograma().encontrarActividadSinMonitor(codigoActividad) == null);
					String codigoMonitor;
					do {
						codigoMonitor = JOptionPane.showInputDialog("Por favor, introduce un id de monitor válido ");
						System.out.println(codigoMonitor);
					} while (parent.getPrograma().encontrarMonitor(codigoMonitor) == null);

					if (!parent.getPrograma().asignarMonitorActividad(codigoMonitor, codigoActividad)) {
					}
				}
			});
		}
		return btnAsignarMonitor;
	}
}
