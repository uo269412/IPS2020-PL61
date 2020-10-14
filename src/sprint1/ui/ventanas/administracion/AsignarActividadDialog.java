package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.JOptionPane;

import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Monitor;
import sprint1.business.clases.Programa;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import sprint1.business.clases.Actividad;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Color;

public class AsignarActividadDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel pnActividadesPlanificadas;
	private JList<ActividadPlanificada> listActividadesPlanificadas;
	private JLabel lblDia;
	private JPanel pnProgramarActividad;
	private JLabel lblActividad;
	private JComboBox<Actividad> cmbActividades;
	private JLabel lblHora;
	private JPanel pnHoras;
	private JTextField txtHoraInicio;
	private JTextField txtHoraFin;
	private JLabel lblLimite;
	private JTextField txtLimitePlazas;
	private JLabel lblMonitor;
	private JComboBox<Monitor> cmbMonitor;
	private JLabel lblNewLabel;
	private JTextPane txtRecursos;
	private JPanel pnAñadir;
	private JButton btnAñadir;
	private JButton btnEliminar;
	private JPanel pnBotones;
	private JButton btnVolver;

	private JButton parent;
	private Programa programa;
	private DefaultListModel<ActividadPlanificada> modeloLista;
	private int dia;
	private int mes;
	private int año;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			AsignarActividadDialog dialog = new AsignarActividadDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public AsignarActividadDialog(JButton parentButton, Programa p, int dia, int mes, int año) {
		this.parent = parentButton;
		this.programa = p;
		this.dia = dia;
		this.mes = mes;
		this.año = año;
		this.modeloLista = new DefaultListModel<ActividadPlanificada>();
		setBounds(100, 100, 450, 311);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		contentPanel.add(getPnProgramarActividad());
		contentPanel.add(getPnActividadesPlanificadas());
		getContentPane().add(getPnBotones(), BorderLayout.SOUTH);
	}

	private JPanel getPnActividadesPlanificadas() {
		if (pnActividadesPlanificadas == null) {
			pnActividadesPlanificadas = new JPanel();
			pnActividadesPlanificadas.setLayout(new BorderLayout(0, 0));
			pnActividadesPlanificadas.add(getListActividadesPlanificadas(), BorderLayout.CENTER);
			pnActividadesPlanificadas.add(getLblDia(), BorderLayout.NORTH);
			pnActividadesPlanificadas.add(getBtnEliminar(), BorderLayout.SOUTH);
		}
		return pnActividadesPlanificadas;
	}

	private JList<ActividadPlanificada> getListActividadesPlanificadas() {
		if (listActividadesPlanificadas == null) {
			mostrarActividadesPlanificadasDia();
			listActividadesPlanificadas = new JList<ActividadPlanificada>();
			listActividadesPlanificadas.setModel(modeloLista);
		}
		return listActividadesPlanificadas;
	}

	private JLabel getLblDia() {
		if (lblDia == null) {
			lblDia = new JLabel("Actividades d\u00EDa:");
		}
		return lblDia;
	}

	private JPanel getPnProgramarActividad() {
		if (pnProgramarActividad == null) {
			pnProgramarActividad = new JPanel();
			pnProgramarActividad.setLayout(new BoxLayout(pnProgramarActividad, BoxLayout.PAGE_AXIS));
			pnProgramarActividad.add(getLblActividad());
			pnProgramarActividad.add(getCmbActividades());
			pnProgramarActividad.add(getLblHora());
			pnProgramarActividad.add(getPnHoras());
			pnProgramarActividad.add(getLblLimite());
			pnProgramarActividad.add(getTxtLimitePlazas());
			pnProgramarActividad.add(getLblMonitor());
			pnProgramarActividad.add(getCmbMonitor());
			pnProgramarActividad.add(getLblNewLabel());
			pnProgramarActividad.add(getTxtRecursos());
			pnProgramarActividad.add(getPnAñadir());
		}
		return pnProgramarActividad;
	}

	private JLabel getLblActividad() {
		if (lblActividad == null) {
			lblActividad = new JLabel("Actividad a asignar:");
		}
		return lblActividad;
	}

	private JComboBox<Actividad> getCmbActividades() {
		if (cmbActividades == null) {
			cmbActividades = new JComboBox<Actividad>();
			cmbActividades.setModel(
					new DefaultComboBoxModel(programa.getActividades().toArray(new Actividad[programa.getActividades().size()])));
		}
		return cmbActividades;
	}

	private JLabel getLblHora() {
		if (lblHora == null) {
			lblHora = new JLabel("Hora inicio/Hora fin (formato HH)");
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

	private JLabel getLblLimite() {
		if (lblLimite == null) {
			lblLimite = new JLabel("Limite de plazas:");
		}
		return lblLimite;
	}

	private JTextField getTxtLimitePlazas() {
		if (txtLimitePlazas == null) {
			txtLimitePlazas = new JTextField();
			txtLimitePlazas.setColumns(10);
		}
		return txtLimitePlazas;
	}

	private JLabel getLblMonitor() {
		if (lblMonitor == null) {
			lblMonitor = new JLabel("Monitor para la actividad:");
		}
		return lblMonitor;
	}

	private JComboBox<Monitor> getCmbMonitor() {
		if (cmbMonitor == null) {
			cmbMonitor = new JComboBox<Monitor>();
		}
		return cmbMonitor;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Recursos (separados por \", \"):");
		}
		return lblNewLabel;
	}

	private JTextPane getTxtRecursos() {
		if (txtRecursos == null) {
			txtRecursos = new JTextPane();
		}
		return txtRecursos;
	}

	private JPanel getPnAñadir() {
		if (pnAñadir == null) {
			pnAñadir = new JPanel();
			pnAñadir.add(getBtnAñadir());
		}
		return pnAñadir;
	}

	private JButton getBtnAñadir() {
		if (btnAñadir == null) {
			btnAñadir = new JButton("A\u00F1adir");
			btnAñadir.setForeground(Color.WHITE);
			btnAñadir.setBackground(new Color(60, 179, 113));
		}
		return btnAñadir;
	}

	private JButton getBtnEliminar() {
		if (btnEliminar == null) {
			btnEliminar = new JButton("Eliminar seleccionada");
			btnEliminar.setForeground(Color.WHITE);
			btnEliminar.setBackground(new Color(255, 99, 71));
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						programa.eliminarActividadPlanificada(listActividadesPlanificadas.getSelectedValue());
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null,
								"Ha ocurrido un error eliminando la actividad. Póngase en contacto con el desarrollador");
					}
					mostrarActividadesPlanificadasDia();
				}
			});
		}
		return btnEliminar;
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
			btnVolver.setBackground(new Color(30, 144, 255));
			btnVolver.setForeground(Color.WHITE);
			btnVolver.setActionCommand("Cancel");
		}
		return btnVolver;
	}

	private void mostrarActividadesPlanificadasDia() {
		try {
			for (ActividadPlanificada a : programa.getActividadesPlanificadasDia(dia, mes, año)) {
				modeloLista.addElement(a);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this,
					"Ha ocurrido un error cargando las actividades para este día, por favor contacte con el desarrollador");
		}
	}
}
