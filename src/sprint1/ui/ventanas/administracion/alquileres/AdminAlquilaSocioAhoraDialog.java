package sprint1.ui.ventanas.administracion.alquileres;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.business.dominio.centroDeportes.alquileres.Alquiler;
import sprint1.business.dominio.centroDeportes.instalaciones.Instalacion;
import sprint1.business.dominio.centroDeportes.reservas.Reserva;
import sprint1.business.dominio.clientes.Socio;
import sprint1.ui.ventanas.administracion.AdminWindow;

public class AdminAlquilaSocioAhoraDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AdminWindow parent = null;
	private Socio socio = null;
	private DefaultListModel<Instalacion> modeloInstalaciones = null;
	private DefaultComboBoxModel<Socio> modeloSocios = null;
	private JPanel pnBotones;
	private JButton btnVolver;
	private JButton btnReservar;
	private JPanel pnSocio;
	private JPanel panel;
	private JScrollPane scpInstalaciones;
	private JList<Instalacion> listInstalaciones;
	private JPanel pnCombo;
	private JComboBox<Socio> comboBox;
	private JLabel lblEstadoInstlacion;

	public AdminAlquilaSocioAhoraDialog(AdminWindow adminWindow) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminAlquilaSocioAhoraDialog.class.getResource("/sprint1/ui/resources/titulo.png")));
		setTitle("Centro de deportes: Alquilando instalaci\u00F3n ahora");
		this.parent = adminWindow;
		setBounds(100, 100, 503, 316);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getPnBotones(), BorderLayout.SOUTH);
		getContentPane().add(getPnSocio(), BorderLayout.NORTH);
		getContentPane().add(getPanel(), BorderLayout.CENTER);
		cargarSocio();
		cargarInstalaciones();
	}

	private void cargarInstalaciones() {
		modeloInstalaciones.clear();
		int fecha[] = getPrograma().obtenerHoraDiaMesAño();
		int hora = fecha[0];
		int dia = fecha[1];
		int mes = fecha[2];
		int año = fecha[3];
		List<ActividadPlanificada> actividadesQueOcurrenAhoraEnInstalacion = new ArrayList<>();
		List<Alquiler> alquileresQueOcurrenAhoraEnInstalacion = new ArrayList<>();
		boolean instalacionOcupada;
		for (Instalacion instalacion : getPrograma().getInstalaciones()) {
			if (instalacion.getEstado() == Instalacion.DISPONIBLE) {
				instalacionOcupada = false;
				actividadesQueOcurrenAhoraEnInstalacion = getPrograma()
						.getActividadesPlanificadas(instalacion.getCodigoInstalacion(), hora, dia, mes, año);
				alquileresQueOcurrenAhoraEnInstalacion = getPrograma().getAlquileres(instalacion.getCodigoInstalacion(),
						hora, dia, mes, año);
				if (!actividadesQueOcurrenAhoraEnInstalacion.isEmpty()) {
					instalacionOcupada = true;
				}
				if (!alquileresQueOcurrenAhoraEnInstalacion.isEmpty()) {
					instalacionOcupada = true;
				}
				if (!instalacionOcupada) {
					modeloInstalaciones.addElement(instalacion);
				}
			}
		}
		if (modeloInstalaciones.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No queda ninguna instalación libre para el momento");
			dispose();
		}
	}

	private boolean checkSePuedeReservar2Horas(Instalacion instalacion) {
		int fecha[] = getPrograma().obtenerHoraDiaMesAño();
		int hora = fecha[0] + 1;
		int dia = fecha[1];
		int mes = fecha[2];
		int año = fecha[3];
		List<ActividadPlanificada> actividadesQueOcurren2HorasDespues = getPrograma()
				.getActividadesPlanificadas(instalacion.getCodigoInstalacion(), hora, dia, mes, año);
		List<Alquiler> alquileresQueOcurren2HorasDespues = getPrograma()
				.getAlquileres(instalacion.getCodigoInstalacion(), hora, dia, mes, año);
		return actividadesQueOcurren2HorasDespues.isEmpty() && alquileresQueOcurren2HorasDespues.isEmpty();
	}

	public boolean comprobarSiSocioPuedeAlquilar(Socio socio) {
		int fecha[] = getPrograma().obtenerHoraDiaMesAño();
		int hora = fecha[0];
		int dia = fecha[1];
		int mes = fecha[2];
		int año = fecha[3];
		List<Reserva> reservasQueOcurrenAhora = getPrograma().getReservas(hora, dia, mes, año);
		List<Alquiler> alquileresQueOcurrenAhora = getPrograma().getAlquileres(hora, dia, mes, año);
		for (Reserva reserva : reservasQueOcurrenAhora) {
			if (socio.getId_cliente().equals(reserva.getId_cliente())) {
				return false;
			}
		}
		for (Alquiler alquiler : alquileresQueOcurrenAhora) {
			if (socio.getId_cliente().equals(alquiler.getId_cliente())) {
				return false;
			}
		}
		return true;
	}

	private void cargarSocio() {
		for (Socio socio : getPrograma().getSocios()) {
			modeloSocios.addElement(socio);
		}
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
			pnBotones.add(getLblEstadoInstlacion());
			pnBotones.add(getBtnVolver());
			pnBotones.add(getBtnReservar());
		}
		return pnBotones;
	}

	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.setMnemonic('V');
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnVolver.setForeground(Color.WHITE);
			btnVolver.setBackground(Color.BLUE);
			btnVolver.setActionCommand("Cancel");
		}
		return btnVolver;
	}

	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Alquilar");
			btnReservar.setMnemonic('A');
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (listInstalaciones.isSelectionEmpty()) {
						JOptionPane.showMessageDialog(null, "Por favor, selecciona tanto una actividad");
					} else {
						int yesNo = JOptionPane.showConfirmDialog(null,
								"¿Seguro de que quieres alquilar la instalación "
										+ listInstalaciones.getSelectedValue().getNombre() + " ?");
						if (yesNo == JOptionPane.YES_OPTION) {
							Instalacion instalacion = listInstalaciones.getSelectedValue();
							int fecha[] = getPrograma().obtenerHoraDiaMesAño();
							if (checkSePuedeReservar2Horas(instalacion)) {
								getPrograma().añadirAlquiler(socio, instalacion, fecha[0], fecha[0] + 2);
								JOptionPane.showMessageDialog(null,
										"Se ha realizado el alquiler de dos horas correctamente");
							} else {
								getPrograma().añadirAlquiler(socio, instalacion, fecha[0], fecha[0] + 1);
								JOptionPane.showMessageDialog(null,
										"Se ha realizado el alquiler de una hora correctamente");
							}
							dispose();

						}
					}
				}
			});
			btnReservar.setBackground(new Color(0, 128, 0));
			btnReservar.setForeground(new Color(255, 255, 255));
		}
		return btnReservar;
	}

	public AdminWindow getParent() {
		return this.parent;
	}

	public Programa getPrograma() {
		return getParent().getParent().getPrograma();
	}

	private JPanel getPnSocio() {
		if (pnSocio == null) {
			pnSocio = new JPanel();
			pnSocio.setBorder(new TitledBorder(null, "Selecci\u00F3n de socio", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnSocio.setLayout(new BorderLayout(0, 0));
			pnSocio.add(getPnCombo(), BorderLayout.CENTER);
		}
		return pnSocio;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Lista de instalaciones disponibles", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getScpInstalaciones());
		}
		return panel;
	}

	private JScrollPane getScpInstalaciones() {
		if (scpInstalaciones == null) {
			scpInstalaciones = new JScrollPane();
			scpInstalaciones.setViewportView(getListInstalaciones());
		}
		return scpInstalaciones;
	}

	private JList<Instalacion> getListInstalaciones() {
		if (listInstalaciones == null) {
			modeloInstalaciones = new DefaultListModel<Instalacion>();
			listInstalaciones = new JList<Instalacion>(modeloInstalaciones);
			listInstalaciones.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent arg0) {
					if (checkSePuedeReservar2Horas(listInstalaciones.getSelectedValue())) {
						lblEstadoInstlacion.setText("Esta instalación se podrá reservar dos horas");
					} else {
						lblEstadoInstlacion.setText("Esta instalación solo se podrá reservar una hora");
					}
				}

			});
			listInstalaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listInstalaciones;
	}

	private JPanel getPnCombo() {
		if (pnCombo == null) {
			pnCombo = new JPanel();
			pnCombo.setLayout(new BorderLayout(0, 0));
			pnCombo.add(getComboBox_1(), BorderLayout.NORTH);
		}
		return pnCombo;
	}

	private JComboBox<Socio> getComboBox_1() {
		if (comboBox == null) {
			modeloSocios = new DefaultComboBoxModel<Socio>();
			comboBox = new JComboBox<Socio>(modeloSocios);
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					actualizarSocios();
					cargarInstalaciones();
				}
			});
		}
		return comboBox;
	}

	private void actualizarSocios() {
		setSocio((Socio) comboBox.getSelectedItem());
		if (!comprobarSiSocioPuedeAlquilar(socio)) {
			btnReservar.setEnabled(false);
			lblEstadoInstlacion.setText(
					"No se puede hacer ningún alquiler ya que tiene una reserva o un alquiler en este momento");
			listInstalaciones.setEnabled(false);
		} else {
			btnReservar.setEnabled(true);
			listInstalaciones.setEnabled(true);
			lblEstadoInstlacion.setText("");
		}
	}

	private void setSocio(Socio selectedItem) {
		this.socio = selectedItem;

	}

	private JLabel getLblEstadoInstlacion() {
		if (lblEstadoInstlacion == null) {
			lblEstadoInstlacion = new JLabel("Esta instalaci\u00F3n solo se podr\u00E1 reservar una hora  ");
			lblEstadoInstlacion.setFont(new Font("Tahoma", Font.ITALIC, 11));
			lblEstadoInstlacion.setForeground(Color.DARK_GRAY);
		}
		return lblEstadoInstlacion;
	}
}
