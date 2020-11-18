package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Alquiler;
import sprint1.business.clases.Programa;
import sprint1.business.clases.Reserva;
import sprint1.business.clases.Socio;
import javax.swing.JComboBox;
import javax.swing.ListModel;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AdminReservaSocioWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AdminWindow parent = null;
	private Socio socio = null;
	private DefaultListModel<ActividadPlanificada> modeloActividades = null;
	private DefaultComboBoxModel<Socio> modeloSocios = null;
	private JPanel pnBotones;
	private JButton btnVolver;
	private JButton btnReservar;
	private JPanel pnSocio;
	private JPanel panel;
	private JScrollPane scpActividades;
	private JList<ActividadPlanificada> listActividades;
	private JLabel lblActividades;
	private JLabel lblNewLabel;
	private JPanel pnCombo;
	private JComboBox<Socio> comboBox;
	private JLabel lblEstadoSocio;

	public AdminReservaSocioWindow(AdminWindow adminWindow) {
		setTitle("Administraci\u00F3n: Realizando reserva para un socio");
		this.parent = adminWindow;
		setBounds(100, 100, 681, 313);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getPnBotones(), BorderLayout.SOUTH);
		getContentPane().add(getPnSocio(), BorderLayout.NORTH);
		getContentPane().add(getPanel(), BorderLayout.CENTER);
		cargarSocio();
	}


	private void cargarActividades() {
		modeloActividades.clear();
		int fecha[] = getPrograma().obtenerHoraDiaMesAño();
		int dia = fecha[1];
		int mes = fecha[2];
		int año = fecha[3];
		List<ActividadPlanificada> actividadesReservadasSocio = getPrograma()
				.getActividadesPlanificadasQueHaReservadoSocioEnUnDiaEspecifico(socio, dia, mes, año);
		List<Alquiler> alquileresSocio = getPrograma().getAlquileres(dia, mes, año);
		List<ActividadPlanificada> actividadesParaHoy = getPrograma().getActividadesPlanificadas(dia, mes, año);

		boolean actividadDisponible = true;

		for (ActividadPlanificada actividad : actividadesParaHoy) {
			actividadDisponible = true;
			for (ActividadPlanificada actividadSocio : actividadesReservadasSocio) {
				if (actividad.equals(actividadSocio)) {
					actividadDisponible = false;
				} else if (getPrograma().comprobarTiempoActividadesColisiona(actividad, actividadSocio)) {
					actividadDisponible = false;
				}
			}
			for (Alquiler alquiler : alquileresSocio) {
				if (socio.getId_cliente().equals(alquiler.getId_cliente())) {
					if (getPrograma().comprobarTiempoActividadyAlquilerColisiona(actividad, alquiler)) {
						actividadDisponible = false;
					}
				}
			}		
			if (actividadDisponible) {
				modeloActividades.addElement(actividad);
			}
		}

	}

	private boolean comprobarSiSocioPuedeReservar() {
		int fecha[] = getPrograma().obtenerHoraDiaMesAño();
		int dia = fecha[1];
		int mes = fecha[2];
		int año = fecha[3];
		List<ActividadPlanificada> actividadesReservadasSocio = getPrograma()
				.getActividadesPlanificadasQueHaReservadoSocioEnUnDiaEspecifico(socio, dia, mes, año);
		List<Alquiler> alquileresSocio = getPrograma().getAlquileres(dia, mes, año);
		List<ActividadPlanificada> actividadesParaHoy = getPrograma().getActividadesPlanificadas(dia, mes, año);

		boolean actividadDisponible = true;

		for (ActividadPlanificada actividad : actividadesParaHoy) {
			actividadDisponible = true;
			for (ActividadPlanificada actividadSocio : actividadesReservadasSocio) {
				if (actividad.equals(actividadSocio)) {
					actividadDisponible = false;
				} else if (getPrograma().comprobarTiempoActividadesColisiona(actividad, actividadSocio)) {
					actividadDisponible = false;
				}
			}
			for (Alquiler alquiler : alquileresSocio) {
				if (socio.getId_cliente().equals(alquiler.getId_cliente())) {
					if (getPrograma().comprobarTiempoActividadyAlquilerColisiona(actividad, alquiler)) {
						actividadDisponible = false;
					}
				}
			}		
			if (actividadDisponible) {
				return true;
			}
		}
		return false;
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
			pnBotones.add(getLblEstadoSocio());
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
			btnReservar = new JButton("Reservar");
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (listActividades.isSelectionEmpty()) {
						JOptionPane.showMessageDialog(null, "Por favor, selecciona tanto una actividad");
					} else {
						int yesNo = JOptionPane.showConfirmDialog(null, "¿Seguro de que reservar para la actividad "
								+ listActividades.getSelectedValue().getCodigoActividad() + " ?");
						if (yesNo == JOptionPane.YES_OPTION) {
							ActividadPlanificada ap = listActividades.getSelectedValue();
							getPrograma().añadirReserva(socio, ap);
							JOptionPane.showMessageDialog(null, "Se ha realizado la reserva correctamente");
							actualizarSocios();

						}
					}
				}
			});
			btnReservar.setBackground(new Color(0, 128, 0));
			btnReservar.setForeground(new Color(255, 255, 255));
		}
		return btnReservar;
	}

	public void añadirReservaLista() {
		getPrograma().addReserva(socio.getId_cliente(), listActividades.getSelectedValue().getCodigoPlanificada());
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
			panel.add(getScpActividades());
		}
		return panel;
	}

	private JScrollPane getScpActividades() {
		if (scpActividades == null) {
			scpActividades = new JScrollPane();
			scpActividades.setViewportView(getListActividades());
			scpActividades.setColumnHeaderView(getLblActividades());
		}
		return scpActividades;
	}

	private JList<ActividadPlanificada> getListActividades() {
		if (listActividades == null) {
			modeloActividades = new DefaultListModel<ActividadPlanificada>();
			listActividades = new JList<ActividadPlanificada>(modeloActividades);
			listActividades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listActividades;
	}

	private JLabel getLblActividades() {
		if (lblActividades == null) {
			lblActividades = new JLabel("Lista de actividades disponibles para el socio:");
			lblActividades.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblActividades;
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
				}
			});
		}
		return comboBox;
	}

	private void actualizarSocios() {
		setSocio((Socio) comboBox.getSelectedItem());
		if (!comprobarSiSocioPuedeReservar()) {
			btnReservar.setEnabled(false);
			lblEstadoSocio.setText("No se puede reservar para este socio");
			cargarActividades();
		} else {
			cargarActividades();
			lblEstadoSocio.setText("");
			btnReservar.setEnabled(true);
		}
	}

	private void setSocio(Socio selectedItem) {
		this.socio = selectedItem;

	}

	private JLabel getLblEstadoSocio() {
		if (lblEstadoSocio == null) {
			lblEstadoSocio = new JLabel("No se puede reservar para este socio");
			lblEstadoSocio.setFont(new Font("Tahoma", Font.ITALIC, 11));
			lblEstadoSocio.setForeground(Color.RED);
		}
		return lblEstadoSocio;
	}
}
