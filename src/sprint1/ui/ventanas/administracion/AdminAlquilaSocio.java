package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Alquiler;
import sprint1.business.clases.Instalacion;
import sprint1.business.clases.Programa;
import sprint1.business.clases.Reserva;
import sprint1.business.clases.Socio;

public class AdminAlquilaSocio extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel pnProgramarActividad;
	private JLabel lblSocio;
	private JComboBox<Socio> cmbSocio;
	private JLabel lblHora;
	private JPanel pnHoras;
	private JTextField txtHoraInicio;
	private JTextField txtHoraFin;
	private JButton btnAñadir;
	private JPanel pnBotones;
	private JButton btnVolver;

	private CalendarioAlquilerAdmin parent;
	private Programa programa;
	private int dia;
	private int mes;
	private int año;

	private JLabel lblInstalacion;
	private JComboBox<Instalacion> cmbInstalaciones;

	private DefaultComboBoxModel<Instalacion> modeloInstalaciones = new DefaultComboBoxModel<>();
	private DefaultComboBoxModel<Socio> modeloSocios = new DefaultComboBoxModel<>();

	/**
	 * Create the dialog.
	 */
	public AdminAlquilaSocio(CalendarioAlquilerAdmin parent, int dia, int mes, int año) {
		setTitle("Centro de deportes: Alquilar para socio");
		this.dia = dia;
		this.mes = mes;
		System.out.println(mes);
		this.año = año;
		this.parent = parent;
		setBounds(100, 100, 707, 342);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		contentPanel.add(getPnProgramarActividad());
		getContentPane().add(getPnBotones(), BorderLayout.SOUTH);
		cargarSocios();
		cargarInstalaciones();
	}

	private void cargarInstalaciones() {
		for (Instalacion instalacion : getPrograma().getInstalaciones()) {
			if (instalacion.getEstado()) {
				modeloInstalaciones.addElement(instalacion);
			}
		}
	}

	private void cargarSocios() {
		for (Socio socio : getPrograma().getSocios()) {
			modeloSocios.addElement(socio);
		}
	}

	private boolean comprobacionesAlquiler() {
		if (!checkHoraInicio()) {
			JOptionPane.showMessageDialog(this, "Los datos introducidos en la hora de inicio no representan un número");
			return false;
		} else if (!checkHoraFin()) {
			JOptionPane.showMessageDialog(this, "Los datos introducidos en la hora de fin no representan un número");
			return false;
		} else if (!checkSocioLibre()) {
			JOptionPane.showMessageDialog(this, "El socio está ocupado en la hora en la se quiere hacer el alquiler");
			return false;
		} else if (!checkInstalacionLibre()) {
			JOptionPane.showMessageDialog(this,
					"La instalación está ocupada a la hora a la que se quiere realizar el alquiler");
			return false;
		} else if (!checkHoraInicioMayorHoraActual()) {
			JOptionPane.showMessageDialog(this, "No puede alquilarse una instalación en el pasado");
			return false;
		} else if (!checkHoraInicioMenorHoraFin()) {
			JOptionPane.showMessageDialog(this, "La hora de fin tiene que ser mayor que la hora de inicio");
			return false;
		} else if (!checkHorasNoSuperan2()) {
			JOptionPane.showMessageDialog(this, "No se puede alquilar por más de dos horas");
			return false;
		}
		return true;
	}

	private boolean checkHoraInicio() {
		try {
			Integer.parseInt(txtHoraInicio.getText());
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	private boolean checkHoraFin() {
		try {
			Integer.parseInt(txtHoraFin.getText());
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	private boolean checkSocioLibre() {
		int horaInicio = Integer.parseInt(txtHoraInicio.getText());
		Socio socio = (Socio) cmbSocio.getSelectedItem();
		List<Reserva> reservasSocio = getPrograma().getReservas(horaInicio, dia, mes, año);
		List<Alquiler> alquileresSocio = getPrograma().getAlquileres(horaInicio, dia, mes, año);
		for (Reserva reserva : reservasSocio) {
			if (socio.getId_cliente().equals(reserva.getId_cliente())) {
				return false;
			}
		}
		for (Alquiler alquiler : alquileresSocio) {
			if (socio.getId_cliente().equals(alquiler.getId_cliente())) {
				return false;
			}
		}
		return true;
	}

	private boolean checkInstalacionLibre() {
		int horaInicio = Integer.parseInt(txtHoraInicio.getText());
		Instalacion instalacion = (Instalacion) cmbInstalaciones.getSelectedItem();
		List<ActividadPlanificada> actividadesInstalacion = getPrograma().getActividadesPlanificadas(horaInicio, dia,
				mes, año);
		List<Alquiler> alquileresInstalacion = getPrograma().getAlquileres(horaInicio, dia, mes, año);
		for (ActividadPlanificada actividadPlanificada : actividadesInstalacion) {
			if (instalacion.getCodigoInstalacion().equals(actividadPlanificada.getCodigoInstalacion())) {
				return false;
			}
		}
		for (Alquiler alquiler : alquileresInstalacion) {
			if (instalacion.getCodigoInstalacion().equals(alquiler.getId_instalacion())) {
				return false;
			}
		}
		return true;
	}

	private boolean checkHorasNoSuperan2() {
		int horaInicio = Integer.parseInt(txtHoraInicio.getText());
		int horaFin = Integer.parseInt(txtHoraFin.getText());
		return (horaFin - horaInicio) <= 2;
	}

	private boolean checkHoraInicioMenorHoraFin() {
		int horaInicio = Integer.parseInt(txtHoraInicio.getText());
		int horaFin = Integer.parseInt(txtHoraFin.getText());
		return horaFin > horaInicio;
	}

	private boolean checkHoraInicioMayorHoraActual() {
		int horaInicio = Integer.parseInt(txtHoraInicio.getText());
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		return horaInicio >= hora;
	}

	private void crearAlquiler() {
		Socio socio = (Socio) cmbSocio.getSelectedItem();
		Instalacion instalacion = (Instalacion) cmbInstalaciones.getSelectedItem();
		int horaInicio = Integer.parseInt(txtHoraInicio.getText());
		int horaFin = Integer.parseInt(txtHoraFin.getText());
		getPrograma().añadirAlquiler(socio, instalacion, horaInicio, horaFin);
	}

	private JPanel getPnProgramarActividad() {
		if (pnProgramarActividad == null) {
			pnProgramarActividad = new JPanel();
			pnProgramarActividad.setLayout(new GridLayout(0, 1, 0, 0));
			pnProgramarActividad.add(getLblSocio());
			pnProgramarActividad.add(getCmbSocio());
			pnProgramarActividad.add(getLblInstalacion());
			pnProgramarActividad.add(getCmbInstalaciones());
			pnProgramarActividad.add(getLblHora());
			pnProgramarActividad.add(getPnHoras());
		}
		return pnProgramarActividad;
	}

	private JLabel getLblSocio() {
		if (lblSocio == null) {
			lblSocio = new JLabel("Socio al que se le quiere asignar el alquiler:  ");
		}
		return lblSocio;
	}

	private JComboBox<Socio> getCmbSocio() {
		if (cmbSocio == null) {
			modeloSocios = new DefaultComboBoxModel<Socio>();
			cmbSocio = new JComboBox<Socio>(modeloSocios);
		}
		return cmbSocio;
	}

	private JLabel getLblHora() {
		if (lblHora == null) {
			lblHora = new JLabel("Hora inicio / Hora fin (formato HH)");
		}
		return lblHora;
	}

	private JPanel getPnHoras() {
		if (pnHoras == null) {
			pnHoras = new JPanel();
			pnHoras.add(getTxtHoraInicio());
			pnHoras.add(getTxtHoraFin());
		}
		return pnHoras;
	}

	private JTextField getTxtHoraInicio() {
		if (txtHoraInicio == null) {
			txtHoraInicio = new JTextField();
			txtHoraInicio.setColumns(10);
		}
		return txtHoraInicio;
	}

	private JTextField getTxtHoraFin() {
		if (txtHoraFin == null) {
			txtHoraFin = new JTextField();
			txtHoraFin.setColumns(10);
		}
		return txtHoraFin;
	}

	private JButton getBtnAñadir() {
		if (btnAñadir == null) {
			btnAñadir = new JButton("A\u00F1adir");
			btnAñadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (comprobacionesAlquiler()) {
						crearAlquiler();
						JOptionPane.showMessageDialog(getMe(), "Se ha añadido el alquiler correctamente");
					}
				}
			});
			btnAñadir.setForeground(Color.WHITE);
			btnAñadir.setBackground(new Color(60, 179, 113));
		}
		return btnAñadir;
	}

	public AdminAlquilaSocio getMe() {
		return this;
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
			pnBotones.add(getBtnVolver());
			pnBotones.add(getBtnAñadir());
		}
		return pnBotones;
	}

	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnVolver.setBackground(new Color(30, 144, 255));
			btnVolver.setForeground(Color.WHITE);
			btnVolver.setActionCommand("Cancel");
		}
		return btnVolver;
	}

	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalaci\u00F3n donde se realizar\u00E1 el alquiler: ");
		}
		return lblInstalacion;
	}

	private JComboBox<Instalacion> getCmbInstalaciones() {
		if (cmbInstalaciones == null) {
			cmbInstalaciones = new JComboBox<Instalacion>(modeloInstalaciones);
		}
		return cmbInstalaciones;
	}

	private Programa getPrograma() {
		return parent.getParent().getParent().getPrograma();
	}
}
