package sprint1.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Monitor;
import sprint1.business.clases.Programa;

public class AsignarMonitorActividadDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel pnBotones;
	private JButton btnVolver;

	private AdminWindow parent;
	private Programa programa;
	private DefaultListModel<ActividadPlanificada> modeloActividades = new DefaultListModel<>();
	private DefaultListModel<Monitor> modeloMonitores = new DefaultListModel<>();
	private JPanel pngGeneral;
	private JPanel pnActividadesPlanificadas;
	private JPanel pnMonitores;
	private JScrollPane scpActividadesPlanificadas;
	private JScrollPane scpMonitores;
	private JLabel lblActividades;
	private JLabel lblMonitores;
	private JList<ActividadPlanificada> listActividades;
	private JList<Monitor> listMonitores;
	private JButton btnAsignar;

	/**
	 * Create the dialog.
	 */
	public AsignarMonitorActividadDialog(AdminWindow parent, Programa programa) {
		this.programa = programa;
		setTitle("Administraci\u00F3n: Asignando monitor");
		this.parent = parent;
		setBounds(100, 100, 450, 311);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getPngGeneral());
		contentPanel.add(getBtnAsignar(), BorderLayout.SOUTH);
		getContentPane().add(getPnBotones(), BorderLayout.SOUTH);
		cargarActividadesEnModelo();

	}

	public AdminWindow getParent() {
		return parent;
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
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
			btnVolver.setBackground(new Color(30, 144, 255));
			btnVolver.setForeground(Color.WHITE);
			btnVolver.setActionCommand("Cancel");
		}
		return btnVolver;
	}

	private JPanel getPngGeneral() {
		if (pngGeneral == null) {
			pngGeneral = new JPanel();
			pngGeneral.setLayout(new GridLayout(1, 0, 0, 0));
			pngGeneral.add(getPnActividadesPlanificadas());
			pngGeneral.add(getPnMonitores());
		}
		return pngGeneral;
	}

	private JPanel getPnActividadesPlanificadas() {
		if (pnActividadesPlanificadas == null) {
			pnActividadesPlanificadas = new JPanel();
			pnActividadesPlanificadas.setLayout(new BorderLayout(0, 0));
			pnActividadesPlanificadas.add(getScpActividadesPlanificadas(), BorderLayout.CENTER);
		}
		return pnActividadesPlanificadas;
	}

	private JPanel getPnMonitores() {
		if (pnMonitores == null) {
			pnMonitores = new JPanel();
			pnMonitores.setLayout(new BorderLayout(0, 0));
			pnMonitores.add(getScpMonitores(), BorderLayout.CENTER);
		}
		return pnMonitores;
	}

	private JScrollPane getScpActividadesPlanificadas() {
		if (scpActividadesPlanificadas == null) {
			scpActividadesPlanificadas = new JScrollPane();
			scpActividadesPlanificadas.setColumnHeaderView(getLblActividades_1_1());
			scpActividadesPlanificadas.setViewportView(getListActividades());
		}
		return scpActividadesPlanificadas;
	}

	private JScrollPane getScpMonitores() {
		if (scpMonitores == null) {
			scpMonitores = new JScrollPane();
			scpMonitores.setColumnHeaderView(getLblMonitores());
			scpMonitores.setViewportView(getListMonitores());
		}
		return scpMonitores;
	}

	private JLabel getLblActividades_1_1() {
		if (lblActividades == null) {
			lblActividades = new JLabel("Actividades planificadas:");
		}
		return lblActividades;
	}

	private JLabel getLblMonitores() {
		if (lblMonitores == null) {
			lblMonitores = new JLabel("Monitores disponibles:");
		}
		return lblMonitores;
	}

	private JList<ActividadPlanificada> getListActividades() {
		if (listActividades == null) {
			modeloActividades = new DefaultListModel<ActividadPlanificada>();
			listActividades = new JList<ActividadPlanificada>(modeloActividades);
			listActividades.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent arg0) {
					cargarMonitoresEnModelo(listActividades.getSelectedValue());
				}
			});
			listActividades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listActividades;
	}

	private void cargarActividadesEnModelo() {
		modeloActividades.clear();
		List<ActividadPlanificada> listaPlanificada = new ArrayList<ActividadPlanificada>();
		listaPlanificada = programa.getActividadesPlanificadas();

		for (ActividadPlanificada ap : listaPlanificada) {
			if (!ap.tieneMonitor() && !modeloActividades.contains(ap)) {
				modeloActividades.addElement(ap);
			}
		}
		if (modeloActividades.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Todas las actividades planificadas ya poseen un monitor");
		}
	}

	private void cargarMonitoresEnModelo(ActividadPlanificada actividadSeleccionada) {
		List<ActividadPlanificada> listaPlanificada = getParent().getParent().getPrograma()
				.getActividadesPlanificadas();
		List<Monitor> listaMonitores = getParent().getParent().getPrograma().getMonitores();
		modeloMonitores.clear();
		for (Monitor monitor : listaMonitores) {
			for (ActividadPlanificada actividad : listaPlanificada) {
				if (actividad.tieneMonitor() && actividad.getCodigoMonitor().equals(monitor.getCodigoMonitor())
						&& (actividadSeleccionada.getFecha().equals(actividad.getFecha()))
						&& ((comprobarTiempoActividadesColisiona(actividadSeleccionada, actividad)))) {
					continue;
				} else {
					if (!modeloMonitores.contains(monitor)) {
						modeloMonitores.addElement(monitor);
					}
				}
			}
		}
		if (modeloMonitores.isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"Todos los monitores ya est�n trabajando en el horario de dicha actividad");
		}
	}

	private boolean comprobarTiempoActividadesColisiona(ActividadPlanificada actividadSeleccionada,
			ActividadPlanificada actividad) {
//		return ((actividadSeleccionada.getHoraInicio() == actividad.getHoraInicio())
//				&& (actividadSeleccionada.getHoraFin() == actividad.getHoraFin()));
		return ((actividadSeleccionada.getHoraInicio() == actividad.getHoraInicio())
				|| (actividadSeleccionada.getHoraFin() == actividad.getHoraFin())
				|| ((actividadSeleccionada.getHoraInicio() < actividad.getHoraInicio())
						&& (actividadSeleccionada.getHoraFin() < actividad.getHoraFin())
						&& (actividadSeleccionada.getHoraFin() > actividad.getHoraInicio()))
				|| ((actividadSeleccionada.getHoraInicio() > actividad.getHoraInicio())
						&& (actividadSeleccionada.getHoraFin() > actividad.getHoraFin())
						&& (actividadSeleccionada.getHoraFin() < actividad.getHoraInicio()))
				|| ((actividadSeleccionada.getHoraInicio() < actividad.getHoraInicio())
						&& (actividadSeleccionada.getHoraFin() > actividad.getHoraFin()))
				|| ((actividadSeleccionada.getHoraInicio() > actividad.getHoraInicio())
						&& (actividadSeleccionada.getHoraFin() < actividad.getHoraFin())));
	}

	private JList<Monitor> getListMonitores() {
		if (listMonitores == null) {
			modeloMonitores = new DefaultListModel<Monitor>();
			listMonitores = new JList<Monitor>(modeloMonitores);
			listMonitores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listMonitores;
	}

	private JButton getBtnAsignar() {
		if (btnAsignar == null) {
			btnAsignar = new JButton("Asignar");
			btnAsignar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int yesNo = JOptionPane.showConfirmDialog(null,
							"Acepta para que el monitor " + listMonitores.getSelectedValue().getNombre()
									+ " imparta clase en la actividad "
									+ listActividades.getSelectedValue().getCodigoActividad());
					if (yesNo == JOptionPane.YES_OPTION) {
						getParent().getParent().getPrograma().asignarMonitorActividad(
								getListMonitores().getSelectedValue(), getListActividades().getSelectedValue());
						programa.encontrarActividadPlanificada(
								listActividades.getSelectedValue().getCodigoPlanificada())
								.setCodigoMonitor(listMonitores.getSelectedValue().getCodigoMonitor());
						JOptionPane.showMessageDialog(null, "El monitor ha sido a�adido correctamente");
						dispose();
					}
				}
			});
			btnAsignar.setForeground(new Color(255, 255, 255));
			btnAsignar.setBackground(new Color(34, 139, 34));
		}
		return btnAsignar;
	}

}
