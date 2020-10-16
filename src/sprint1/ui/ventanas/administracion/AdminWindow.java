package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sprint1.business.clases.Socio;
import sprint1.ui.ventanas.MainWindow;

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
	private AdminReservaSocioWindow adminReservaSocio;
	private JButton btnReservaSocio;

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
			pnAcciones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnAcciones.add(getBtnAsignarMonitor_1());
			pnAcciones.add(getBtnCrearActividad());
			pnAcciones.add(getBtnAsignarActividad());
			pnAcciones.add(getBtnReservaSocio());
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
		asignarMonitor = new AsignarMonitorActividadDialog(this);
		asignarMonitor.setModal(true);
		asignarMonitor.setLocationRelativeTo(this);
		asignarMonitor.setVisible(true);
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
					getMe().setModal(false);
					CalendarioAdmin ca = new CalendarioAdmin(getMe());
					ca.setModal(true);
					ca.setVisible(true);
				}
			});
			btnAsignarActividad.setMnemonic('A');
		}
		return btnAsignarActividad;
	}

	private AdminWindow getMe() {
		return this;
	}
	
	public void openAdminReservaSocioWindow(Socio socio) {
		adminReservaSocio = new AdminReservaSocioWindow(this, socio);
		adminReservaSocio.setModal(true);
		adminReservaSocio.setLocationRelativeTo(this);
		adminReservaSocio.setVisible(true);
	}

	private JButton getBtnReservaSocio() {
		if (btnReservaSocio == null) {
			btnReservaSocio = new JButton("Hacer una reserva para un socio en una actividad que se realiza hoy");
			btnReservaSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String id_socio;
					do {
						id_socio = JOptionPane.showInputDialog("Por favor, introduce un id de socio válido ");
					} while (getParent().getPrograma().encontrarSocio(id_socio) == null);
					openAdminReservaSocioWindow(getParent().getPrograma().encontrarSocio(id_socio));
				}
			});
		}
		return btnReservaSocio;
	}
}
