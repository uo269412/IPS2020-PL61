package sprint1.ui.ventanas.administracion.actividades;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.actividades.Actividad;
import sprint1.business.dominio.centroDeportes.instalaciones.Recurso;

public class CrearActividadDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2207372092080235648L;

	Programa p = null;

	public static final int LONGITUDMAX_CODIGO_ACTIVIDAD = 26;
	public static final int LONGITUDMAX_NOMBRE_ACTIVIDAD = 26;

	private final JPanel pnRegistrarActividad = new JPanel();
	private JTextField txtNombre;
	private JTextField txtIntensidad;
	private JComboBox<Recurso> cmbRecursos;
	
	private DefaultListModel<Recurso> modelRecursos;
	private JList<Recurso> listRecursos;

	/**
	 * Create the dialog.
	 */
	public CrearActividadDialog(Programa p) {
		this.p = p;
		this.modelRecursos = new DefaultListModel<Recurso>();
		setTitle("Administrador: Crear actividad");
		setModal(true);
		setBounds(100, 100, 483, 499);
		getContentPane().setLayout(new BorderLayout());
		pnRegistrarActividad.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnRegistrarActividad, BorderLayout.CENTER);
		pnRegistrarActividad.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JLabel lblNombre = new JLabel("Nombre de la actividad:");
			pnRegistrarActividad.add(lblNombre);
		}
		{
			txtNombre = new JTextField();
			pnRegistrarActividad.add(txtNombre);
			txtNombre.setColumns(10);
		}
		{
			JLabel lblIntensidad = new JLabel("Intensidad de la actividad (0, 1 o 2):");
			pnRegistrarActividad.add(lblIntensidad);
		}
		{
			txtIntensidad = new JTextField();
			pnRegistrarActividad.add(txtIntensidad);
			txtIntensidad.setColumns(10);
		}
		{
			JLabel lblRecursos = new JLabel("Recursos (separados por \", \"):");
			pnRegistrarActividad.add(lblRecursos);
		}
		{
			JPanel pnRecursos = new JPanel();
			pnRegistrarActividad.add(pnRecursos);
			pnRecursos.setLayout(new BorderLayout(0, 0));
			{
				cmbRecursos = new JComboBox<Recurso>(new DefaultComboBoxModel<Recurso>((Recurso[])p.getRecursosSinActividad().toArray(new Recurso[p.getRecursosSinActividad().size()])));
				pnRecursos.add(cmbRecursos);
			}
			{
				JButton btnAñadirRecurso = new JButton("A\u00F1adir recurso");
				btnAñadirRecurso.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						añadirRecursos();
					}
				});
				btnAñadirRecurso.setBackground(new Color(60, 179, 113));
				btnAñadirRecurso.setForeground(Color.WHITE);
				pnRecursos.add(btnAñadirRecurso, BorderLayout.SOUTH);
			}
		}
		{
			listRecursos = new JList<Recurso>(modelRecursos);
			listRecursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			pnRegistrarActividad.add(listRecursos);
		}
		{
			JButton btnEliminarRecurso = new JButton("Eliminar recurso seleccionado");
			btnEliminarRecurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					eliminarRecursos();
				}
			});
			btnEliminarRecurso.setForeground(new Color(255, 255, 255));
			btnEliminarRecurso.setBackground(new Color(255, 99, 71));
			pnRegistrarActividad.add(btnEliminarRecurso);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						recolorFields();
						if (!checkEverything()) {
							JOptionPane.showMessageDialog(getCrearActividadDialog(),
									"Error creando la actividad. Por favor revise los campos resaltados.");
						} else {
							String nombreActividad = txtNombre.getText();
							int intensidad = Integer.parseInt(txtIntensidad.getText());
							
							boolean creacionActividadSatisfactoria = true;
							Actividad a = crearActividad(nombreActividad, intensidad);
							if (p.encontrarActividad(a.getCodigo()) != null) {
								JOptionPane.showMessageDialog(getCrearActividadDialog(),
										"Error: una actividad con este codigo ya se encuentra en la base de datos");
								creacionActividadSatisfactoria = false;
							}
							if (creacionActividadSatisfactoria) {

								try {

									p.insertarActividad(a);
									JOptionPane.showMessageDialog(getCrearActividadDialog(),
											"Actividad creada correctamente");
									restablishFields();
								} catch (SQLException e) {
									JOptionPane.showMessageDialog(getCrearActividadDialog(),
											"Ha habido un problema con la base de datos,"
													+ "por favor póngase en contacto con el desarrollador.");
									e.printStackTrace();
								}
							}

						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private Actividad crearActividad(String nombreActividad, int intensidad) {
		if(modelRecursos.getSize() == 0) {
			return new Actividad(nombreActividad, intensidad);
		} else {
			
			try {
				Actividad a = new Actividad(nombreActividad, intensidad);
				
				List<Recurso> toIterate = new ArrayList<>();
				
				for(int i = 0; i < modelRecursos.size(); i++) {
					toIterate.add(modelRecursos.get(i));
				}
				
				for(Recurso r: toIterate) {
					a.añadirRecurso(r);
				}
				p.updateRecursosFromLista(a.getRecursos());
				return a;
			} catch(SQLException e) {
				JOptionPane.showMessageDialog(this,
						"Ha habido un problema con la base de datos comprobando los recursos, por favor contacte con el desarrollador");
				return new Actividad(nombreActividad, intensidad);
			}
			
		}
	}
	
	
	public CrearActividadDialog getCrearActividadDialog() {
		return this;
	}

	private void recolorFields() {
		for (int i = 0; i < pnRegistrarActividad.getComponentCount(); i++) {
			if (pnRegistrarActividad.getComponent(i) instanceof JTextField) {
				JTextField toRestore = (JTextField) pnRegistrarActividad.getComponent(i);
				toRestore.setBackground(Color.WHITE);
				toRestore.setForeground(Color.BLACK);
			}
		}
	}

	private void restablishFields() {
		for (int i = 0; i < pnRegistrarActividad.getComponentCount(); i++) {
			if (pnRegistrarActividad.getComponent(i) instanceof JTextField) {
				JTextField toRestore = (JTextField) pnRegistrarActividad.getComponent(i);
				toRestore.setText("");
			} else if(pnRegistrarActividad.getComponent(i) instanceof JTextPane) {
				JTextPane toRestore = (JTextPane) pnRegistrarActividad.getComponent(i);
				toRestore.setText("");
			}
		}
	}

	private boolean checkEverything() {
		return checkNombre() //&& checkHoraInicio() && checkHoraFin() && checkLimitePlazas()
				&& checkIntensidad();
	}

	private boolean checkNombre() { // restricciones del nombre de la actividad
		if (txtNombre.getText().length() > LONGITUDMAX_NOMBRE_ACTIVIDAD) {
			txtNombre.setBackground(Color.RED);
			txtNombre.setForeground(Color.WHITE);
			return false;
		}

		return true;
	}

/*
	private boolean checkHoraInicio() {
		if (txtInicio.getText().length() > 2) {
			txtInicio.setBackground(Color.RED);
			txtInicio.setForeground(Color.WHITE);
			return false;
		}

		try {
			Integer.parseInt(txtInicio.getText());
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	private boolean checkHoraFin() {
		if (txtFinal.getText().length() > 2) {
			txtFinal.setBackground(Color.RED);
			txtFinal.setForeground(Color.WHITE);
			return false;
		}

		try {
			Integer.parseInt(txtFinal.getText());
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	private boolean checkLimitePlazas() {
		try {
			Integer.parseInt(txtLimite.getText());
		} catch (NumberFormatException e) {
			txtLimite.setBackground(Color.RED);
			txtLimite.setForeground(Color.WHITE);
			return false;
		}

		return true;
	}
	
	
	private boolean checkRecursos() { // IMPORTANTE hay que definir un parámetro de la longitud de la id para hacer
										// una comprobación más.
		try {
			String rawRecursos = txtRecursos.getText();
			String[] idRecursos = rawRecursos.split(", ");
		} catch (Exception e) {
			txtRecursos.setBackground(Color.RED);
			txtRecursos.setForeground(Color.WHITE);
			return false;
		}

		return true;
	}

	*/
	
	
	private boolean checkIntensidad() {

		if (txtIntensidad.getText().length() > 1) {
			txtIntensidad.setBackground(Color.RED);
			txtIntensidad.setForeground(Color.WHITE);
			return false;
		}

		int intensidad;
		try {
			intensidad = Integer.parseInt(txtIntensidad.getText());
		} catch (NumberFormatException e) {
			txtIntensidad.setBackground(Color.RED);
			txtIntensidad.setForeground(Color.WHITE);
			return false;
		}

		if (intensidad != Actividad.INTENSIDAD_BAJA && intensidad != Actividad.INTENSIDAD_MODERADA
				&& intensidad != Actividad.INTENSIDAD_ALTA) {
			txtIntensidad.setBackground(Color.RED);
			txtIntensidad.setForeground(Color.WHITE);
			return false;
		}

		return true;
	}
	
	public void añadirRecursos() {
		modelRecursos.addElement((Recurso)cmbRecursos.getSelectedItem());
	}
	
	public void eliminarRecursos() {
		modelRecursos.remove(listRecursos.getSelectedIndex());
	}
	
}

