package sprint1.ui.ventanas.administracion.actividades;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.actividades.Actividad;
import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.business.dominio.centroDeportes.instalaciones.Instalacion;
import sprint1.business.dominio.centroDeportes.instalaciones.Recurso;
import sprint1.ui.ventanas.administracion.util.CalendarioAdmin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.Toolkit;

public class PlanificarActividadDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private JPanel pnA�adir;
	private JButton btnA�adir;
	private JButton btnEliminar;
	private JPanel pnBotones;
	private JButton btnVolver;

	private Programa programa;
	private DefaultListModel<ActividadPlanificada> modeloLista;
	private int dia;
	private int mes;
	private int a�o;
	private JButton btnNewButton;

	private List<ActividadPlanificada> aEliminar;
	private JLabel lblInstalacion;
	private JComboBox<Instalacion> cmbInstalaciones;
	
	private boolean updated = true;
	DefaultComboBoxModel<Actividad> modeloBaseComboActividades;
	private JScrollPane scrollPane;
	
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
	public PlanificarActividadDialog(CalendarioAdmin parent, Programa p, int dia, int mes, int a�o) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PlanificarActividadDialog.class.getResource("/sprint1/ui/resources/titulo.png")));
		setTitle("Centro de deportes: Asignar actividad " + dia + "/" + mes + "/" + a�o);
		this.programa = p;
		this.dia = dia;
		this.mes = mes;
		this.a�o = a�o;
		this.modeloLista = new DefaultListModel<ActividadPlanificada>();
		this.modeloBaseComboActividades = new DefaultComboBoxModel<Actividad>(
				programa.getActividades().toArray(new Actividad[programa.getActividades().size()]));
		setBounds(100, 100, 760, 331);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		contentPanel.add(getPnProgramarActividad());
		contentPanel.add(getPnActividadesPlanificadas());
		getContentPane().add(getPnBotones(), BorderLayout.SOUTH);
		aEliminar = new LinkedList<>();
	}

	private JPanel getPnActividadesPlanificadas() {
		if (pnActividadesPlanificadas == null) {
			pnActividadesPlanificadas = new JPanel();
			pnActividadesPlanificadas.setLayout(new BorderLayout(0, 0));
			pnActividadesPlanificadas.add(getLblDia(), BorderLayout.NORTH);
			pnActividadesPlanificadas.add(getBtnEliminar(), BorderLayout.SOUTH);
			pnActividadesPlanificadas.add(getScrollPane(), BorderLayout.CENTER);
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
			pnProgramarActividad.add(getPnA�adir());
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
					if(updated) {
						if(cmbActividades.getSelectedItem() != null) {
							rellenarInstalacion();
						}
					}
				}
			});
			cmbActividades.setFont(new Font("Tahoma", Font.PLAIN, 8));
			cmbActividades.setModel(modeloBaseComboActividades);
			
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

	private JPanel getPnA�adir() {
		if (pnA�adir == null) {
			pnA�adir = new JPanel();
			pnA�adir.add(getBtnA�adir());
		}
		return pnA�adir;
	}

	private JButton getBtnA�adir() {
		if (btnA�adir == null) {
			btnA�adir = new JButton("A\u00F1adir");
			btnA�adir.setMnemonic('d');
			if(cmbInstalaciones.getModel().getSize() == 0) {
				btnA�adir.setEnabled(false);
			} else {
				btnA�adir.setEnabled(true);
			}
			btnA�adir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					if (checkHoraInicio() && checkHoraFin() && checkLimitePlazas() == 0) {
						updated = false;
						cmbInstalaciones.setEnabled(false);
						a�adirAModelo(crearPlanificada());
						adaptarActividadesAInstalacion();
						repintar();
					} else if(checkLimitePlazas() == 1) {
						JOptionPane.showMessageDialog(getMe(), "La aplicaci�n tiene menos recursos disponibles que las plazas que est�s intentando a�adir.\n"
								+ "M�ximos recursos disponibles ahora mismo en la instalaci�n: " + getMinRecursos());
					}
					
				}
			});
			btnA�adir.setForeground(Color.WHITE);
			btnA�adir.setBackground(new Color(60, 179, 113));
		}
		return btnA�adir;
	}
	
	private void repintar() {
		txtHoraInicio.setBackground(Color.WHITE);
		txtHoraInicio.setForeground(Color.BLACK);
		txtHoraInicio.setText("");
		txtHoraFin.setBackground(Color.WHITE);
		txtHoraFin.setForeground(Color.BLACK);
		txtHoraFin.setText("");
		txtLimitePlazas.setBackground(Color.WHITE);
		txtLimitePlazas.setForeground(Color.BLACK);
		txtLimitePlazas.setText("");
		
	}

	private JButton getBtnEliminar() {
		if (btnEliminar == null) {
			btnEliminar = new JButton("Eliminar seleccionada");
			btnEliminar.setMnemonic('E');
			btnEliminar.setForeground(Color.WHITE);
			btnEliminar.setBackground(new Color(255, 99, 71));
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					updated = false;
					cmbInstalaciones.setEnabled(false);
					cmbInstalaciones.setToolTipText("No puedes cambiar de instalaci�n hasta que no hayas aplicado los cambios");
					int indiceAEliminar = listActividadesPlanificadas.getSelectedIndex();
					aEliminar.add(modeloLista.get(indiceAEliminar));
					modeloLista.remove(indiceAEliminar);
					adaptarActividadesAInstalacion();
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
			btnVolver.setMnemonic('V');
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					getMe().dispose();
				}
			});
			btnVolver.setBackground(Color.BLUE);
			btnVolver.setForeground(Color.WHITE);
			btnVolver.setActionCommand("Cancel");
		}
		return btnVolver;
	}

	private PlanificarActividadDialog getMe() {
		return this;
	}

	private void a�adirAModelo(ActividadPlanificada a) {
		List<ActividadPlanificada> lista = new LinkedList<>();
		for(int i = 0; i < modeloLista.getSize(); i++) {
			lista.add(modeloLista.getElementAt(i));
		}
		 
		lista.add(a);
		lista.sort(new ComparePlanificadas());
		modeloLista.clear();
		for(ActividadPlanificada actividad: lista) {
			modeloLista.add(modeloLista.size(), actividad);
		}
		
	}
	
	public void mostrarActividadesPlanificadasDia() {
		/*
		try {
			for (ActividadPlanificada a : programa.getActividadesPlanificadasInstalacionDia(((Instalacion)cmbInstalaciones.getSelectedItem()).getCodigo(), dia, mes, a�o)) {
				modeloLista.addElement(a);
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this,
					"Ha ocurrido un error cargando las actividades para este d�a, por favor contacte con el desarrollador");
		}
		*/
		modeloLista.clear();
		List<ActividadPlanificada> actividades = new LinkedList<>();
		for (ActividadPlanificada a : programa.getActividadesPlanificadasInstalacionDia(((Instalacion)cmbInstalaciones.getSelectedItem()).getCodigo(), dia, mes, a�o)) {
			actividades.add(a);
		}
		actividades.sort(new ComparePlanificadas());
		for(ActividadPlanificada a: actividades) {
			modeloLista.addElement(a);
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

	private int checkLimitePlazas() {
		if(txtLimitePlazas.getText().contentEquals("")) {
			return 0;
		}
		else {
			try {
				int limitePlazas = Integer.parseInt(txtLimitePlazas.getText());
				int minRecursos = getMinRecursos();
				
				if(limitePlazas > minRecursos) {
					return 1;
				}
			} catch (NumberFormatException e) {
				txtLimitePlazas.setBackground(Color.RED);
				txtLimitePlazas.setForeground(Color.WHITE);
				return 2;
			}

			return 0;
		}
	}
	
	private int getMinRecursos() {
		int minRecursos = Integer.MAX_VALUE;
		for(Recurso r: ((Actividad)cmbActividades.getSelectedItem()).getRecursos()) {
			if(r.getUnidades() < minRecursos) {
				minRecursos = r.getUnidades();
			}
		}
		
		return minRecursos;
	}

	private ActividadPlanificada crearPlanificada() {
		String codigoAAsignar = ((Actividad) cmbActividades.getSelectedItem()).getCodigo();
		int limitePlazas;
		if(txtLimitePlazas.getText().equals("")){
			limitePlazas = Integer.MAX_VALUE;
		}
		else {
			limitePlazas = Integer.parseInt(txtLimitePlazas.getText());
		}
		int horaInicio = Integer.parseInt(txtHoraInicio.getText());
		int horaFin = Integer.parseInt(txtHoraFin.getText());
		String codigoInstalacion = ((Instalacion)cmbInstalaciones.getSelectedItem()).getCodigoInstalacion();
		return new ActividadPlanificada(codigoAAsignar, dia, mes, a�o, limitePlazas, horaInicio, horaFin, codigoInstalacion);

	}

	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Aplicar cambios");
			btnNewButton.setBackground(new Color(0, 128, 0));
			btnNewButton.setForeground(Color.WHITE);
			btnNewButton.setMnemonic('A');
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					aplicarCambios();
					updated = true;
					cmbActividades.setModel(modeloBaseComboActividades);
					cmbInstalaciones.setEnabled(true);
					cmbInstalaciones.setToolTipText(null);
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
					if( !programa.getActividadesPlanificadas().contains(modeloLista.getElementAt(i)))
						programa.a�adirActividadPlanificada(modeloLista.getElementAt(i));
				}
				modeloLista.clear();
				mostrarActividadesPlanificadasDia();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this,
						"Ha ocurrido un error cargando las actividades para este d�a, por favor contacte con el desarrollador");
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
							+ ", p�ngase en contacto con el desarrollador");
				}
			} else {
				cmbInstalaciones.setEnabled(false);
				cmbInstalaciones.setToolTipText("No hay ninguna instalaci�n que tenga recursos para esta actividad");
				btnA�adir.setEnabled(false);
			}
		} else {
			cmbInstalaciones.setModel(new DefaultComboBoxModel<Instalacion>(programa.getInstalacionesDisponibles().toArray(new Instalacion[programa.getInstalacionesDisponibles().size()])));
		}
	}
	
	private JComboBox<Instalacion> getCmbInstalaciones() {
		if (cmbInstalaciones == null) {
			cmbInstalaciones = new JComboBox<Instalacion>();
			rellenarInstalacion();
			cmbInstalaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
						mostrarActividadesPlanificadasDia();
					}
				});
		}
		return cmbInstalaciones;
	}
	
	private void adaptarActividadesAInstalacion() {
		Instalacion i = (Instalacion) cmbInstalaciones.getSelectedItem();
		cmbActividades.setModel(new DefaultComboBoxModel<Actividad>(
				programa.actividadesPorInstalacion(i.getCodigo()).toArray(new Actividad[programa.actividadesPorInstalacion(i.getCodigo()).size()])));
	}
	
	private class ComparePlanificadas implements Comparator<ActividadPlanificada> {

		@Override
		public int compare(ActividadPlanificada arg0, ActividadPlanificada arg1) {
			return arg0.compareTo(arg1);
		}
		
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.add(getListActividadesPlanificadas());
			scrollPane.setViewportView(getListActividadesPlanificadas());
		}
		return scrollPane;
	}
}