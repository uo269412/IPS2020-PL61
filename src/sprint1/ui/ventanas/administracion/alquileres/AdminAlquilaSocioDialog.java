package sprint1.ui.ventanas.administracion.alquileres;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.business.dominio.centroDeportes.alquileres.Alquiler;
import sprint1.business.dominio.centroDeportes.instalaciones.Instalacion;
import sprint1.business.dominio.centroDeportes.reservas.Reserva;
import sprint1.business.dominio.clientes.Socio;
import sprint1.ui.ventanas.administracion.util.CalendarioAdminAlquilar;

public class AdminAlquilaSocioDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel pnProgramarActividad;
	private JComboBox<Socio> cmbSocio;
	private JPanel pnHoras;
	private JTextField txtHoraInicio;
	private JTextField txtHoraFin;
	private JButton btnAñadir;
	private JPanel pnBotones;
	private JButton btnVolver;

	private CalendarioAdminAlquilar parent;
	private int dia;
	private int mes;
	private int año;
	private JComboBox<Instalacion> cmbInstalaciones;

	private DefaultComboBoxModel<Instalacion> modeloInstalaciones = new DefaultComboBoxModel<>();
	private DefaultComboBoxModel<Socio> modeloSocios = new DefaultComboBoxModel<>();
	private JPanel pnSocio;
	private JPanel pnInstalacion;
	private JPanel pnHoraInicio;
	private JPanel pnHoraFin;

	/**
	 * Create the dialog.
	 */
	public AdminAlquilaSocioDialog(CalendarioAdminAlquilar parent, int dia, int mes, int año) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminAlquilaSocioDialog.class.getResource("/sprint1/ui/resources/titulo.png")));
		setTitle("Centro de deportes: Alquilar para socio");
		this.dia = dia;
		this.mes = mes;
		this.año = año;
		this.parent = parent;
		setBounds(100, 100, 397, 264);
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
			if (instalacion.getEstado() == Instalacion.DISPONIBLE) {
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
		int horaFin = Integer.parseInt(txtHoraFin.getText());
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
		reservasSocio = getPrograma().getReservas();
		alquileresSocio = getPrograma().getAlquileresSocioEnUnDiaEspecifico(socio, dia, mes, año);
		for (Reserva reserva : reservasSocio) {
			if (socio.getId_cliente().equals(reserva.getId_cliente())) {
				if (chocaHoras(reserva, horaInicio, horaFin))
					return false;
			}
		}
		for (Alquiler alquiler : alquileresSocio) {
			if (socio.getId_cliente().equals(alquiler.getId_cliente())) {
				if (chocaHoras(alquiler, horaInicio, horaFin))
					return false;
			}
		}
		return true;
	}
	
	private boolean chocaHoras(Alquiler alquiler, int horaInicio, int horaFin) {
		for (int hora = alquiler.getHoraInicio(); hora < alquiler.getHoraFin(); hora++) {
			if (hora >= horaInicio && hora < horaFin)
				return true;
		}
		return false;
	}

	private boolean chocaHoras(Reserva reserva, int horaInicio, int horaFin) {
		List<ActividadPlanificada> actividades = getPrograma().getActividadesPlanificadas();
		for (ActividadPlanificada ap : actividades) {
			if (reserva.getCodigo_actividad().equals(ap.getCodigoActividad())) {
				for (int hora = ap.getHoraInicio(); hora < ap.getHoraFin(); hora++) {
					if (hora >= horaInicio && hora < horaFin)
						return true;
				}
			}
		}
		return false;
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
		int[] fecha = getPrograma().obtenerHoraDiaMesAño();
		if (año == fecha[3]) {
			if (mes == fecha[2]) {
				if (dia == fecha[1]) {
					return horaInicio >= fecha[0];
				} else if (fecha[1] > dia) {
					return false;
				}
			} else if (fecha[2] > mes) {
				return false;
			}
		} else if (fecha[3] > año) {
			return false;
		}
		return true;
	}

	private void crearAlquiler() {
		Socio socio = (Socio) cmbSocio.getSelectedItem();
		Instalacion instalacion = (Instalacion) cmbInstalaciones.getSelectedItem();
		int horaInicio = Integer.parseInt(txtHoraInicio.getText());
		int horaFin = Integer.parseInt(txtHoraFin.getText());
		getPrograma().añadirAlquiler(socio, instalacion, horaInicio, horaFin, dia, mes, año);
	}

	private JPanel getPnProgramarActividad() {
		if (pnProgramarActividad == null) {
			pnProgramarActividad = new JPanel();
			pnProgramarActividad.setLayout(new GridLayout(0, 1, 0, 0));
			pnProgramarActividad.add(getPnSocio());
			pnProgramarActividad.add(getPnInstalacion());
			pnProgramarActividad.add(getPnHoras());
		}
		return pnProgramarActividad;
	}

	private JComboBox<Socio> getCmbSocio() {
		if (cmbSocio == null) {
			modeloSocios = new DefaultComboBoxModel<Socio>();
			cmbSocio = new JComboBox<Socio>(modeloSocios);
		}
		return cmbSocio;
	}

	private JPanel getPnHoras() {
		if (pnHoras == null) {
			pnHoras = new JPanel();
			pnHoras.setLayout(new GridLayout(0, 2, 0, 0));
			pnHoras.add(getPnHoraInicio());
			pnHoras.add(getPnHoraFin());
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

	public AdminAlquilaSocioDialog getMe() {
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

	private JComboBox<Instalacion> getCmbInstalaciones() {
		if (cmbInstalaciones == null) {
			cmbInstalaciones = new JComboBox<Instalacion>(modeloInstalaciones);
		}
		return cmbInstalaciones;
	}

	private Programa getPrograma() {
		return parent.getParent().getParent().getPrograma();
	}
	private JPanel getPnSocio() {
		if (pnSocio == null) {
			pnSocio = new JPanel();
			pnSocio.setBorder(new TitledBorder(null, "Socio al que se le quiere asignar el alquiler", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnSocio.setLayout(new BorderLayout(0, 0));
			pnSocio.add(getCmbSocio());
		}
		return pnSocio;
	}
	private JPanel getPnInstalacion() {
		if (pnInstalacion == null) {
			pnInstalacion = new JPanel();
			pnInstalacion.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Instalaci\u00F3n donde se realizar\u00E1 el alquiler", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnInstalacion.setLayout(new BorderLayout(0, 0));
			pnInstalacion.add(getCmbInstalaciones());
		}
		return pnInstalacion;
	}
	private JPanel getPnHoraInicio() {
		if (pnHoraInicio == null) {
			pnHoraInicio = new JPanel();
			pnHoraInicio.setBorder(new TitledBorder(null, "Hora inicio", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnHoraInicio.add(getTxtHoraInicio());
		}
		return pnHoraInicio;
	}
	private JPanel getPnHoraFin() {
		if (pnHoraFin == null) {
			pnHoraFin = new JPanel();
			pnHoraFin.setBorder(new TitledBorder(null, "Hora fin", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnHoraFin.add(getTxtHoraFin());
		}
		return pnHoraFin;
	}
}
