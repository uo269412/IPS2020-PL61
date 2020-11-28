package sprint1.ui.ventanas.socio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.business.dominio.centroDeportes.alquileres.Alquiler;
import sprint1.business.dominio.centroDeportes.reservas.Reserva;
import sprint1.business.dominio.clientes.Socio;
import sprint1.ui.ventanas.MainWindow;
import sprint1.ui.ventanas.socio.acciones.ListaDeReservasWindow;
import sprint1.ui.ventanas.socio.acciones.ListaDeAlquileresWindow;
import sprint1.ui.ventanas.socio.acciones.ReservaSocioWindow;
import sprint1.ui.ventanas.socio.util.CalendarioAlquilerSocio;
import sprint1.ui.ventanas.socio.vistas.VerAlquileresSocio;
import sprint1.ui.ventanas.socio.vistas.VerHorariosSocioWindow;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;

public class SocioWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private MainWindow parent = null;
	private Socio socio;
	private JPanel pnTextos;
	private JLabel lblBienvenida;
	private JLabel lblAcciones;
	private JPanel pnFuncionalidad;
	private JPanel pnLogOut;
	private JPanel pnAcciones;
	private JButton btnLogOut;
	private JButton btnVerActividades;
	private ListaDeReservasWindow listaDeActividadesWindow = null;
	private ListaDeAlquileresWindow listaDeAlquileresWindow = null;
	private JButton btnVerHorariosSocios;
	private JButton btnReservar;
	private JButton btnCancelarAlquiler;
	private JButton btnVerAlquileres;
	private JButton btnAlquilar;
	private JPanel pnActividades;
	private JPanel pnVista;
	private JPanel pnAlquileres;

	/**
	 * Create the dialog.
	 */
	public SocioWindow(MainWindow mainWindow, Socio socio) {
		setTitle("Centro de Deportes: Acceso como socio");
		this.parent = mainWindow;
		this.socio = socio;
		setBounds(100, 100, 365, 307);
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
			lblBienvenida = new JLabel("Bienvenido, socio " + socio.getNombre());
			lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
			lblBienvenida.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return lblBienvenida;
	}

	private JLabel getLblAcciones() {
		if (lblAcciones == null) {
			lblAcciones = new JLabel("Acciones disponibles como socio:");
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
			pnAcciones.setLayout(new GridLayout(0, 1, 0, 0));
			pnAcciones.add(getPnActividades());
			pnAcciones.add(getPnAlquileres());
			pnAcciones.add(getPnVista());

		}
		return pnAcciones;
	}

	private JButton getBtnLogOut() {
		if (btnLogOut == null) {
			btnLogOut = new JButton("Cerrar sesi\u00F3n");
			btnLogOut.setMnemonic('s');
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

	public MainWindow getParent() {
		return parent;
	}

	private void openListaDeActividadesWindow() {
		if (checkIfListWillOpen()) {
			listaDeActividadesWindow = new ListaDeReservasWindow(this, socio);
			listaDeActividadesWindow.setModal(true);
			listaDeActividadesWindow.setLocationRelativeTo(this);
			listaDeActividadesWindow.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(this, "No tienes ninguna reserva de ninguna actividad");
		}

	}

	public boolean checkIfListWillOpen() {
		List<String> codigosActividades = new ArrayList<String>();
		for (Reserva reserva : getParent().getPrograma().getReservas()) {
			if (reserva.getId_cliente().equals(socio.getId_cliente())) {
				codigosActividades.add(reserva.getCodigo_actividad());
			}
		}
		if (codigosActividades.isEmpty()) {
			return false;
		} else {
			for (String codigo : codigosActividades) {
				for (ActividadPlanificada actividad : getParent().getPrograma().getActividadesPlanificadas()) {
					if (codigo.equals(actividad.getCodigoPlanificada())
							&& getParent().getPrograma().comprobarActividadAntesQueFecha(actividad)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private JButton getBtnVerActividades() {
		if (btnVerActividades == null) {
			btnVerActividades = new JButton("Ver y cancelar reservas");
			btnVerActividades.setBackground(new Color(25, 25, 112));
			btnVerActividades.setForeground(new Color(255, 255, 255));
			btnVerActividades.setMnemonic('V');
			btnVerActividades.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					openListaDeActividadesWindow();
				}
			});
		}
		return btnVerActividades;
	}

	private JButton getBtnVerHorariosSocios() {
		if (btnVerHorariosSocios == null) {
			btnVerHorariosSocios = new JButton("Ver horarios");
			btnVerHorariosSocios.setForeground(new Color(255, 255, 255));
			btnVerHorariosSocios.setBackground(new Color(25, 25, 112));
			btnVerHorariosSocios.setMnemonic('h');
			btnVerHorariosSocios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VerHorariosSocioWindow vosw = new VerHorariosSocioWindow(
							SocioWindow.this.getParent().getPrograma());
					vosw.setModal(true);
					vosw.setLocationRelativeTo(SocioWindow.this);
					vosw.setVisible(true);
				}
			});
		}
		return btnVerHorariosSocios;
	}

	private JButton getButton_1() {
		if (btnReservar == null) {
			btnReservar = new JButton("Reservar actividad");
			btnReservar.setForeground(new Color(255, 255, 255));
			btnReservar.setBackground(new Color(25, 25, 112));
			btnReservar.setMnemonic('R');
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ReservaSocioWindow rsw = new ReservaSocioWindow(SocioWindow.this, socio);
					rsw.setModal(true);
					rsw.setLocationRelativeTo(SocioWindow.this);
					rsw.setVisible(true);
				}
			});
		}
		return btnReservar;
	}

	private JButton getBtnCancelarAlquiler() {
		if (btnCancelarAlquiler == null) {
			btnCancelarAlquiler = new JButton("Ver y cancelar alquileres");
			btnCancelarAlquiler.setForeground(new Color(255, 255, 255));
			btnCancelarAlquiler.setBackground(new Color(25, 25, 112));
			btnCancelarAlquiler.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					openListaAlquileresWindow();
				}
			});
			btnCancelarAlquiler.setMnemonic('q');
		}
		return btnCancelarAlquiler;
	}

	private void openListaAlquileresWindow() {
		if (checkIfListaAlquileresAbre()) {
			listaDeAlquileresWindow = new ListaDeAlquileresWindow(this, socio);
			listaDeAlquileresWindow.setModal(true);
			listaDeAlquileresWindow.setLocationRelativeTo(this);
			listaDeAlquileresWindow.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(this, "No tienes ningun alquiler de ninguna instalación");
		}
	}

	public boolean checkIfListaAlquileresAbre() {
		List<Alquiler> alquileres = new ArrayList<Alquiler>();
		for (Alquiler alquiler : getParent().getPrograma().getAlquileres()) {
			if (alquiler.getId_cliente().equals(socio.getId_cliente())) {
				if (getParent().getPrograma().comprobarAlquilerAPartirDeHoy(alquiler)) {
					alquileres.add(alquiler);
				}
			}
		}
		if (alquileres.isEmpty()) {
			return false;
		}
		return true;
	}
	private JButton getBtnVerAlquileres() {
		if (btnVerAlquileres == null) {
			btnVerAlquileres = new JButton("Ver historial de alquileres");
			btnVerAlquileres.setForeground(new Color(255, 255, 255));
			btnVerAlquileres.setBackground(new Color(25, 25, 112));
			btnVerAlquileres.setMnemonic('t');
			btnVerAlquileres.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VerAlquileresSocio vas = new VerAlquileresSocio(SocioWindow.this, socio);
					vas.setModal(true);
					vas.setLocationRelativeTo(SocioWindow.this);
					vas.setVisible(true);
				}
			});
		}
		return btnVerAlquileres;
	}
	private JButton getBtnAlquilar() {
		if (btnAlquilar == null) {
			btnAlquilar = new JButton("Alquilar instalaci\u00F3n");
			btnAlquilar.setForeground(new Color(255, 255, 255));
			btnAlquilar.setBackground(new Color(25, 25, 112));
			btnAlquilar.setMnemonic('A');
			btnAlquilar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					openSocioAlquilaWindow();
				}
			});
		}
		return btnAlquilar;
	}
	private void openSocioAlquilaWindow() {
		CalendarioAlquilerSocio cas = new CalendarioAlquilerSocio(this, socio);
		cas.setModal(true);
		cas.setLocationRelativeTo(this);
		cas.setVisible(true);
	}
	private JPanel getPnActividades() {
		if (pnActividades == null) {
			pnActividades = new JPanel();
			pnActividades.setBackground(Color.WHITE);
			pnActividades.setBorder(new TitledBorder(null, "Reservas y actividades del centro deportivo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnActividades.add(getButton_1());
			pnActividades.add(getBtnVerActividades());
		}
		return pnActividades;
	}
	private JPanel getPnVista() {
		if (pnVista == null) {
			pnVista = new JPanel();
			pnVista.setBackground(Color.WHITE);
			pnVista.setBorder(new TitledBorder(null, "Ver horario y alquileres", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnVista.add(getBtnVerHorariosSocios());
			pnVista.add(getBtnVerAlquileres());
		}
		return pnVista;
	}
	private JPanel getPnAlquileres() {
		if (pnAlquileres == null) {
			pnAlquileres = new JPanel();
			pnAlquileres.setBackground(Color.WHITE);
			pnAlquileres.setBorder(new TitledBorder(null, "Alquileres de instalaciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnAlquileres.add(getBtnAlquilar());
			pnAlquileres.add(getBtnCancelarAlquiler());
		}
		return pnAlquileres;
	}
}