package sprint1.ui;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Font;

public class AdminWindow extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow parent = null;
	private JPanel pnTextos;
	private JLabel lblBienvenida;
	private JLabel lblAcciones;
	private JPanel pnFuncionalidad;
	private JPanel pnLogOut;
	private JPanel pnAcciones;
	private JButton btnLogOut;
	private JButton btnAsignarMonitor;
	private JButton btnCrearActividad;
	private JButton btnAsignarActividad;
	
	private CalendarioAdmin calendarAdmin;
	private AsignarMonitorActividadDialog asignarMonitor;

	/**
	 * Create the dialog.
	 */
	public AdminWindow(MainWindow mainWindow) {
		setTitle("Centro de Deportes: Administrador");
		this.parent = mainWindow;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnTextos(), BorderLayout.NORTH);
		getContentPane().add(getPnFuncionalidad(), BorderLayout.CENTER);
	}

	private JPanel getPnTextos() {
		if (pnTextos == null) {
			pnTextos = new JPanel();
			pnTextos.setBackground(Color.WHITE);
			pnTextos.setLayout(new BorderLayout(0, 0));
			pnTextos.add(getLblBienvenida(), BorderLayout.NORTH);
			pnTextos.add(getLblAcciones());
		}
		return pnTextos;
	}

	private JLabel getLblBienvenida() {
		if (lblBienvenida == null) {
			lblBienvenida = new JLabel("Bienvenido, administrador");
		}
		return lblBienvenida;
	}

	private JLabel getLblAcciones() {
		if (lblAcciones == null) {
			lblAcciones = new JLabel("Acciones disponibles como administrador:");
			lblAcciones.setFont(new Font("Arial", Font.BOLD, 14));
			lblAcciones.setHorizontalAlignment(SwingConstants.CENTER);
			lblAcciones.setBackground(Color.WHITE);
		}
		return lblAcciones;
	}

	private JPanel getPnFuncionalidad() {
		if (pnFuncionalidad == null) {
			pnFuncionalidad = new JPanel();
			pnFuncionalidad.setLayout(new BorderLayout(0, 0));
			pnFuncionalidad.add(getPnLogOut(), BorderLayout.SOUTH);
			pnFuncionalidad.add(getPnAcciones(), BorderLayout.CENTER);
		}
		return pnFuncionalidad;
	}

	private JPanel getPnLogOut() {
		if (pnLogOut == null) {
			pnLogOut = new JPanel();
			pnLogOut.setBackground(new Color(255, 255, 255));
			pnLogOut.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnLogOut.add(getBtnLogOut());
		}
		return pnLogOut;
	}

	private JPanel getPnAcciones() {
		if (pnAcciones == null) {
			pnAcciones = new JPanel();
			pnAcciones.setBackground(new Color(255, 255, 255));
			pnAcciones.add(getBtnAsignarMonitor_1());
			pnAcciones.add(getBtnCrearActividad());
			pnAcciones.add(getBtnAsignarActividad());
		}
		return pnAcciones;
	}

	private JButton getBtnLogOut() {
		if (btnLogOut == null) {
			btnLogOut = new JButton("Log Out");
			btnLogOut.setMnemonic('O');
			btnLogOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnLogOut.setHorizontalAlignment(SwingConstants.RIGHT);
			btnLogOut.setForeground(Color.WHITE);
			btnLogOut.setBackground(new Color(255, 99, 71));
		}
		return btnLogOut;
	}

	private JButton getBtnAsignarMonitor_1() {
		if (btnAsignarMonitor == null) {
			btnAsignarMonitor = new JButton("Asignar monitor a actividad");
			btnAsignarMonitor.setMnemonic('M');
			btnAsignarMonitor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					openAsignarMonitorActividadWindow();
				}
			});
		}
		return btnAsignarMonitor;
	}
	
	public void openAsignarMonitorActividadWindow() {
		asignarMonitor = new AsignarMonitorActividadDialog(this, this.getParent().getPrograma());
		asignarMonitor.setModal(true);
		asignarMonitor.setLocationRelativeTo(this);
		asignarMonitor.setVisible(true);
	}

	private void asignandoMonitorAdministracion() {
//		String codigoActividad;
//		String codigoMonitor;
//		do {
//			codigoActividad = JOptionPane.showInputDialog("Por favor, introduce una id de actividad ya planificada válida ");
//			System.out.println(codigoActividad);
//		} while (parent.getPrograma().encontrarActividadPlanificada(codigoActividad) == null);
//		do {
//			codigoMonitor = JOptionPane.showInputDialog("Por favor, introduce un id de monitor válido ");
//			System.out.println(codigoMonitor);
//		} while (parent.getPrograma().encontrarMonitor(codigoMonitor) == null);
//		getParent().getPrograma().asignarMonitorActividad(codigoMonitor, codigoActividad);
	}

	private JButton getBtnCrearActividad() {
		if (btnCrearActividad == null) {
			btnCrearActividad = new JButton("Crear actividad");
			btnCrearActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CrearActividadDialog dialog = new CrearActividadDialog(parent.getPrograma());
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});
			btnCrearActividad.setMnemonic('C');
		}
		return btnCrearActividad;
	}

	public MainWindow getParent() {
		return this.parent;
	}
	private JButton getBtnAsignarActividad() {
		if (btnAsignarActividad == null) {
			btnAsignarActividad = new JButton("Asignar actividad");
			btnAsignarActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
			btnAsignarActividad.setMnemonic('A');
		}
		return btnAsignarActividad;
	}
}
