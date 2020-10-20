package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
import sprint1.business.clases.Programa;
import sprint1.business.clases.Socio;

public class AdminReservaSocioWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AdminWindow parent = null;
	private Socio socio = null;
	private JScrollPane scpActividades;
	private JList<ActividadPlanificada> listActividades;
	private DefaultListModel<ActividadPlanificada> modeloActividades = null;
	private JPanel pnBotones;
	private JButton btnVolver;
	private JButton btnReservar;
	private JLabel lblSocio;

	public AdminReservaSocioWindow(AdminWindow adminWindow, Socio socio) {
		setTitle("Administraci\u00F3n: Realizando reserva para un socio");
		this.parent = adminWindow;
		this.socio = socio;
		setBounds(100, 100, 681, 588);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getScpActividades(), BorderLayout.CENTER);
		getContentPane().add(getPnBotones(), BorderLayout.SOUTH);
		getContentPane().add(getLblSocio(), BorderLayout.NORTH);
		cargarActividades();
	}

	private JScrollPane getScpActividades() {
		if (scpActividades == null) {
			scpActividades = new JScrollPane();
			scpActividades.setViewportView(getListActividades());
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

	private void cargarActividades() {
		modeloActividades.clear();
		int fecha[] = getPrograma().obtenerHoraDiaMesAño();
		int dia = fecha[1];
		int mes = fecha[2];
		int año = fecha[3];
		List<ActividadPlanificada> actividadesYaReservadasPorSocio = new ArrayList<ActividadPlanificada>();
		actividadesYaReservadasPorSocio = getPrograma()
				.getActividadesPlanificadasQueHaReservadoSocioEnUnDiaEspecifico(socio, dia, mes, año);
		for (ActividadPlanificada actividadPosible : getPrograma().getActividadesPlanificadas(dia, mes, año)) {
			if (actividadPosible.getHoraInicio() > fecha[0] && actividadPosible.getLimitePlazas() != 0) {
				if (actividadesYaReservadasPorSocio.isEmpty()) {
					modeloActividades.addElement(actividadPosible);
				} else if (!actividadesYaReservadasPorSocio.contains(actividadPosible)) {
					for (ActividadPlanificada actividadYaReservada : actividadesYaReservadasPorSocio) {
						if (!getPrograma().comprobarTiempoActividadesColisiona(actividadPosible,
								actividadYaReservada)) {
							modeloActividades.addElement(actividadPosible);
						}
					}
				}
			}
		}
		if (modeloActividades.isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"No se puede realizar ninguna reserva para este usuario");
			dispose();
		}
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
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
						JOptionPane.showMessageDialog(null,
								"Por favor, selecciona tanto una actividad");
					} else {
					int yesNo = JOptionPane.showConfirmDialog(null, "¿Seguro de que reservar para la actividad "
							+ listActividades.getSelectedValue().getCodigoActividad() + " ?");
					if (yesNo == JOptionPane.YES_OPTION) {
						ActividadPlanificada ap = listActividades.getSelectedValue();
						getPrograma().añadirReserva(socio, ap);
						JOptionPane.showMessageDialog(null, "Se ha realizado la reserva correctamente");
						cargarActividades();

					}
				} }
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

	private JLabel getLblSocio() {
		if (lblSocio == null) {
			lblSocio = new JLabel(
					"Realizando una reserva para el socio " + socio.getNombre() + " " + socio.getApellido());
		}
		return lblSocio;
	}

	public Programa getPrograma() {
		return getParent().getParent().getPrograma();
	}
}
