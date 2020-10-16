package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.clases.Actividad;
import sprint1.business.clases.Programa;
import sprint1.business.clases.Recurso;

import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class CrearActividadDialog extends JDialog {

	Programa p = null;

	public static final int LONGITUDMAX_CODIGO_ACTIVIDAD = 26;
	public static final int LONGITUDMAX_NOMBRE_ACTIVIDAD = 16;

	private final JPanel pnRegistrarActividad = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtIntensidad;
	private JTextPane txtRecursos;

	/**
	 * Create the dialog.
	 */
	public CrearActividadDialog(Programa p) {
		this.p = p;
		setTitle("Administrador: Crear actividad");
		setModal(true);
		setBounds(100, 100, 300, 298);
		getContentPane().setLayout(new BorderLayout());
		pnRegistrarActividad.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnRegistrarActividad, BorderLayout.CENTER);
		pnRegistrarActividad.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JLabel lblCodigo = new JLabel("C\u00F3digo de la actividad:");
			pnRegistrarActividad.add(lblCodigo);
		}
		{
			txtCodigo = new JTextField();
			pnRegistrarActividad.add(txtCodigo);
			txtCodigo.setColumns(1);
		}
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
			txtRecursos = new JTextPane();
			pnRegistrarActividad.add(txtRecursos);
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
							String codigoActividad = txtCodigo.getText();
							String nombreActividad = txtNombre.getText();
							int intensidad = Integer.parseInt(txtIntensidad.getText());
							String recursos = txtRecursos.getText();
							
							boolean creacionActividadSatisfactoria = true;
							Actividad a = crearActividad(codigoActividad, nombreActividad, intensidad, recursos);
							if (p.encontrarActividad(a.getCodigo()) != null) {
								JOptionPane.showMessageDialog(getCrearActividadDialog(),
										"Error: una actividad con este codigo ya se encuentra en la base de datos");
								creacionActividadSatisfactoria = false;
							}
							if (creacionActividadSatisfactoria) {
//								String[] recursos = txtRecursos.getText().split(", ");
//								for (String r : recursos) {
//									Recurso rec = new Recurso(r, codigoActividad);
//									a.añadirRecurso(rec);
//								}

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

	private Actividad crearActividad(String codigoActividad, String nombreActividad, int intensidad, String recursos) {
		if(recursos.equals("")) {
			return new Actividad(codigoActividad, nombreActividad, intensidad);
		} else {
			String[] nombreRecursosArray = recursos.split(", ");
			Recurso[] recursosArray = new Recurso[nombreRecursosArray.length];
			for(int i = 0; i < nombreRecursosArray.length; i++) {
				recursosArray[i] = new Recurso(nombreRecursosArray[i]);
			}
			
			return new Actividad(codigoActividad, nombreActividad, intensidad, recursosArray);
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
			}
		}
	}

	private boolean checkEverything() {
		return checkCodigo() && checkNombre() //&& checkHoraInicio() && checkHoraFin() && checkLimitePlazas()
				&& checkIntensidad() && checkRecursos();
	}

	private boolean checkCodigo() { // restricciones del codigo de la actividad
		if (txtCodigo.getText().length() > LONGITUDMAX_CODIGO_ACTIVIDAD) {
			txtCodigo.setBackground(Color.RED);
			txtCodigo.setForeground(Color.WHITE);
			return false;
		}

		return true;
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
	
	private boolean checkRecursos() { // IMPORTANTE hay que definir un parámetro de la longitud de la id para hacer una comprobación más.
		if(txtRecursos.getText().equals("")) {
			return true;
		} else {
			String rawRecursos = txtRecursos.getText();
			String[] idRecursos = rawRecursos.split(", ");
			return true;
		}
		
	}
}
