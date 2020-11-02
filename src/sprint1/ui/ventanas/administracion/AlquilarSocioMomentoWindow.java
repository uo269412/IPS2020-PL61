package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.SwingConstants;

import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Alquiler;
import sprint1.business.clases.Instalacion;
import sprint1.business.clases.Programa;
import sprint1.business.clases.Reserva;
import sprint1.business.clases.Socio;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class AlquilarSocioMomentoWindow extends JDialog {

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
	private JLabel lblInstalaciones;
	private JLabel lblNewLabel;
	private JPanel pnCombo;
	private JComboBox<Socio> comboBox;
	private JLabel lblEstadoInstlacion;

	public AlquilarSocioMomentoWindow(AdminWindow adminWindow) {
		setTitle("Administraci\u00F3n: Realizando un alquiler para el socio ahora");
		this.parent = adminWindow;
		setBounds(100, 100, 681, 313);
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
		int hora = fecha[0] + 1;
		int dia = fecha[1];
		int mes = fecha[2];
		int año = fecha[3];
		List<ActividadPlanificada> actividadesQueOcurrenAhoraEnInstalacion = new ArrayList<>();
		List<Alquiler> alquileresQueOcurrenAhoraEnInstalacion = new ArrayList<>();
		boolean instalacionOcupada;
		for (Instalacion instalacion : getPrograma().getInstalaciones()) {
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
		if (modeloInstalaciones.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No queda ninguna instalación libre para el momento");
			dispose();
		}
	}

	private boolean checkSePuedeReservar2Horas(Instalacion instalacion) {
		int fecha[] = getPrograma().obtenerHoraDiaMesAño();
		int hora = fecha[0] + 2;
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
		int hora = fecha[0] + 1;
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
			pnBotones.add(getBtnReservar());
			pnBotones.add(getBtnVolver());
		}
		return pnBotones;
	}

	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnVolver.setForeground(Color.WHITE);
			btnVolver.setBackground(new Color(30, 144, 255));
			btnVolver.setActionCommand("Cancel");
		}
		return btnVolver;
	}

	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Alquilar");
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
								getPrograma().añadirAlquiler(socio, instalacion, fecha[0] + 1, fecha[0] + 3);
								JOptionPane.showMessageDialog(null,
										"Se ha realizado el alquiler de dos horas correctamente");
							} else {
								getPrograma().añadirAlquiler(socio, instalacion, fecha[0] + 1, fecha[0] + 2);
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
			pnSocio.setLayout(new BorderLayout(0, 0));
			pnSocio.add(getLblNewLabel(), BorderLayout.WEST);
			pnSocio.add(getPnCombo(), BorderLayout.CENTER);
		}
		return pnSocio;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getScpInstalaciones());
		}
		return panel;
	}

	private JScrollPane getScpInstalaciones() {
		if (scpInstalaciones == null) {
			scpInstalaciones = new JScrollPane();
			scpInstalaciones.setViewportView(getListInstalaciones());
			scpInstalaciones.setColumnHeaderView(getLblInstalaciones());
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

	private JLabel getLblInstalaciones() {
		if (lblInstalaciones == null) {
			lblInstalaciones = new JLabel("Lista de instalaciones que est\u00E1n disponibles en el momento");
			lblInstalaciones.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblInstalaciones;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Selecci\u00F3n de socio:   ");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblNewLabel;
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
