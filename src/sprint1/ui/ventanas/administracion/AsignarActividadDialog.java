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
import java.util.LinkedList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;

import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Instalacion;
import sprint1.business.clases.Monitor;
import sprint1.business.clases.Programa;
import sprint1.business.clases.Recurso;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import sprint1.business.clases.Actividad;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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
	private JPanel pnAñadir;
	private JButton btnAñadir;
	private JButton btnEliminar;
	private JPanel pnBotones;
	private JButton btnVolver;

	private CalendarioAdmin parent;
	private Programa programa;
	private DefaultListModel<ActividadPlanificada> modeloLista;
	private int dia;
	private int mes;
	private int año;
	private JButton btnNewButton;

	private List<ActividadPlanificada> aEliminar;
	private JLabel lblInstalacion;
	private JComboBox<Instalacion> cmbInstalaciones;

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
	public AsignarActividadDialog(CalendarioAdmin parent, Programa p, int dia, int mes, int año) {
		setTitle("Centro de deportes: Asignar actividad " + dia + "/" + mes + "/" + año);
		this.parent = parent;
		this.programa = p;
		this.dia = dia;
		this.mes = mes;
		this.año = año;
		this.modeloLista = new DefaultListModel<ActividadPlanificada>();
		setBounds(100, 100, 760, 331);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		contentPanel.add(getPnProgramarActividad());
		contentPanel.add(getPnActividadesPlanificadas());
		getContentPane().add(getPnBotones(), BorderLayout.SOUTH);
		aEliminar = new LinkedList<>();
		rellenarInstalacion();
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
			listActividadesPlanificadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
			pnProgramarActividad.setLayout(new GridLayout(0, 1, 0, 0));
			pnProgramarActividad.add(getLblActividad());
			pnProgramarActividad.add(getCmbActividades());
			pnProgramarActividad.add(getLblHora());
			pnProgramarActividad.add(getPnHoras());
			pnProgramarActividad.add(getLblLimite());
			pnProgramarActividad.add(gettxtLimitePlazasPlazas());
			pnProgramarActividad.add(getLblInstalacion());
			pnProgramarActividad.add(getCmbInstalaciones());
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
			cmbActividades.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(cmbActividades.getSelectedItem() != null) {
						rellenarInstalacion();
					}
				}
			});
			cmbActividades.setFont(new Font("Tahoma", Font.PLAIN, 8));
			cmbActividades.setModel(new DefaultComboBoxModel<Actividad>(
					programa.getActividades().toArray(new Actividad[programa.getActividades().size()])));
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

	private JTextField gettxtLimitePlazasPlazas() {
		if (txtLimitePlazas == null) {
			txtLimitePlazas = new JTextField();
			txtLimitePlazas.setColumns(10);

		}
		return txtLimitePlazas;
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
			btnAñadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					if (checkHoraInicio() && checkHoraFin() && checkLimitePlazas()) {
						// cuando se implemente lo de los monitores, cambiar el constructor
						modeloLista.add(modeloLista.size(), crearPlanificada());
					}
				}
			});
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

					int indiceAEliminar = listActividadesPlanificadas.getSelectedIndex();
					aEliminar.add(modeloLista.get(indiceAEliminar));
					modeloLista.remove(indiceAEliminar);
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
			pnBotones.add(getBtnNewButton());
		}
		return pnBotones;
	}

	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					getMe().dispose();
				}
			});
			btnVolver.setBackground(Color.LIGHT_GRAY);
			btnVolver.setForeground(Color.BLACK);
			btnVolver.setActionCommand("Cancel");
		}
		return btnVolver;
	}

	private AsignarActividadDialog getMe() {
		return this;
	}

	public void mostrarActividadesPlanificadasDia() {
		modeloLista.clear();
		try {
			for (ActividadPlanificada a : programa.getActividadesPlanificadasDia(dia, mes, año)) {
				modeloLista.addElement(a);
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this,
					"Ha ocurrido un error cargando las actividades para este día, por favor contacte con el desarrollador");
		}
	}

	private boolean checkHoraInicio() {
		if (txtHoraInicio.getText().length() > 2) {
			txtHoraInicio.setBackground(Color.RED);
			txtHoraInicio.setForeground(Color.WHITE);
			return false;
		}

		try {
			Integer.parseInt(txtHoraInicio.getText());
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	private boolean checkHoraFin() {
		if (txtHoraFin.getText().length() > 2) {
			txtHoraFin.setBackground(Color.RED);
			txtHoraFin.setForeground(Color.WHITE);
			return false;
		}

		try {
			Integer.parseInt(txtHoraFin.getText());
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	private boolean checkLimitePlazas() {
		try {
			Integer.parseInt(txtLimitePlazas.getText());
		} catch (NumberFormatException e) {
			txtLimitePlazas.setBackground(Color.RED);
			txtLimitePlazas.setForeground(Color.WHITE);
			return false;
		}

		return true;
	}

	private ActividadPlanificada crearPlanificada() {
		String codigoAAsignar = ((Actividad) cmbActividades.getSelectedItem()).getCodigo();
		int limitePlazas = Integer.parseInt(txtLimitePlazas.getText());
		int horaInicio = Integer.parseInt(txtHoraInicio.getText());
		int horaFin = Integer.parseInt(txtHoraFin.getText());
		String codigoInstalacion = ((Instalacion)cmbInstalaciones.getSelectedItem()).getCodigoInstalacion();
		return new ActividadPlanificada(codigoAAsignar, dia, mes, año, limitePlazas, horaInicio, horaFin, codigoInstalacion);

	}

	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Aplicar cambios");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					aplicarCambios();
					dispose();
				}
			});
		}
		return btnNewButton;
	}

	private void aplicarCambios() {
		
			try {
				
				for (ActividadPlanificada a : aEliminar) {
					programa.eliminarActividadPlanificada(a);
				}
				
				for (int i = 0; i < modeloLista.size(); i++) {
					programa.añadirActividadPlanificada(modeloLista.getElementAt(i));
				}
				
				mostrarActividadesPlanificadasDia();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this,
						"Ha ocurrido un error cargando las actividades para este día, por favor contacte con el desarrollador");
			}
			
			listActividadesPlanificadas.validate();
	}
	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalaci\u00F3n:");
		}
		return lblInstalacion;
	}
	
	private void rellenarInstalacion() {
		cmbInstalaciones.setEnabled(true);
		btnAñadir.setEnabled(true);
		Actividad a = programa.getActividades().get(cmbActividades.getSelectedIndex());
		if(a.requiresRecursos()) {
			boolean todosIguales = true;
			String i = a.getRecursos().get(0).getInstalacion();
			for(Recurso r: a.getRecursos()) {
				if(r.getInstalacion().equals(i)) {
					todosIguales = true;
				} else {
					todosIguales = false;
					break;
				}
			}
			if(todosIguales) {
				try {
					Instalacion inst = programa.obtenerInstalacionPorId(a.getRecursos().get(0).getInstalacion());
					Instalacion[] arrayInst = new Instalacion[1];
					arrayInst[0] = inst;
					cmbInstalaciones.setModel(new DefaultComboBoxModel<Instalacion>(arrayInst));
				} catch(SQLException e) {
					JOptionPane.showMessageDialog(this, "Ha habido un problema con la base de datos asignando la instalacion"
							+ ", póngase en contacto con el desarrollador");
				}
			} else {
				cmbInstalaciones.setEnabled(false);
				cmbInstalaciones.setToolTipText("No hay ninguna instalación que tenga recursos para esta actividad");
				btnAñadir.setEnabled(false);
			}
		} else {
			cmbInstalaciones.setModel(new DefaultComboBoxModel<Instalacion>(programa.getInstalaciones().toArray(new Instalacion[programa.getInstalaciones().size()])));
		}
	}
	
	private JComboBox<Instalacion> getCmbInstalaciones() {
		if (cmbInstalaciones == null) {
			cmbInstalaciones = new JComboBox<Instalacion>();
		}
		return cmbInstalaciones;
	}
}
