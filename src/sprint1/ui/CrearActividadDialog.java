package sprint1.ui;

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
import java.awt.event.ActionEvent;

public class CrearActividadDialog extends JDialog {
	
	Programa p = null;
	
	public static final int LONGITUDMAX_CODIGO_ACTIVIDAD = 26;
	public static final int LONGITUDMAX_NOMBRE_ACTIVIDAD = 16;

	private final JPanel pnRegistrarActividad = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtInicio;
	private JTextField txtFinal;
	private JTextField txtLimite;
	private JTextField txtIntensidad;
	private JTextField txtRecursos;


	/**
	 * Create the dialog.
	 */
	public CrearActividadDialog(Programa p) {
		this.p = p;
		setTitle("Administrador: Crear actividad");
		setModal(true);
		setBounds(100, 100, 302, 391);
		getContentPane().setLayout(new BorderLayout());
		pnRegistrarActividad.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnRegistrarActividad, BorderLayout.CENTER);
		pnRegistrarActividad.setLayout(new BoxLayout(pnRegistrarActividad, BoxLayout.PAGE_AXIS));
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
			JLabel lblHoraInicio = new JLabel("Hora de inicio de la actividad (formato: HH):");
			pnRegistrarActividad.add(lblHoraInicio);
		}
		{
			txtInicio = new JTextField();
			pnRegistrarActividad.add(txtInicio);
			txtInicio.setColumns(10);
		}
		{
			JLabel lblHoraFinal = new JLabel("Hora del final de la actividad (formato: HH):");
			pnRegistrarActividad.add(lblHoraFinal);
		}
		{
			txtFinal = new JTextField();
			pnRegistrarActividad.add(txtFinal);
			txtFinal.setColumns(10);
		}
		{
			JLabel lblLimitePlazas = new JLabel("L\u00EDmite de plazas de la actividad:");
			pnRegistrarActividad.add(lblLimitePlazas);
		}
		{
			txtLimite = new JTextField();
			pnRegistrarActividad.add(txtLimite);
			txtLimite.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("Intensidad de la actividad (0, 1 o 2):");
			pnRegistrarActividad.add(lblNewLabel);
		}
		{
			txtIntensidad = new JTextField();
			pnRegistrarActividad.add(txtIntensidad);
			txtIntensidad.setColumns(10);
		}
		{
			JLabel lblRecursos = new JLabel("Recursos (separados por coma-espacio):");
			pnRegistrarActividad.add(lblRecursos);
		}
		{
			txtRecursos = new JTextField();
			pnRegistrarActividad.add(txtRecursos);
			txtRecursos.setColumns(10);
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
						if(!checkEverything()) {
							JOptionPane.showMessageDialog(getCrearActividadDialog(), "Error creando la actividad. Por favor revise los campos resaltados.");
						}
						else {
							String codigoActividad = txtCodigo.getText();
							String nombreActividad = txtNombre.getText();
							int horaInicio = Integer.parseInt(txtInicio.getText());
							int horaFin = Integer.parseInt(txtFinal.getText());
							int limitePlazas = Integer.parseInt(txtLimite.getText());
							int intensidad = Integer.parseInt(txtIntensidad.getText());
							
							boolean creacionActividadSatisfactoria = true;
							Actividad a = new Actividad(codigoActividad, nombreActividad, horaInicio, horaFin, limitePlazas, intensidad);
							for(Actividad x: p.getActividades()) {
								if(a.equals(x)) {
									JOptionPane.showMessageDialog(getCrearActividadDialog(), "Error: una actividad con este codigo ya se encuentra en la base de datos");
									creacionActividadSatisfactoria = false;
								}
							}
							
							if(creacionActividadSatisfactoria) {
								String[] recursos = txtRecursos.getText().split(", ");
								for(String r: recursos) {
									Recurso rec = new Recurso(r, codigoActividad);
									a.añadirRecurso(rec);
								}
								
								try {
									
									p.insertarActividad(a);
									JOptionPane.showMessageDialog(getCrearActividadDialog(), "Actividad creada correctamente");
									restablishFields();
								} catch (SQLException e) {
									JOptionPane.showMessageDialog(getCrearActividadDialog(), "Ha habido un problema con la base de datos,"
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
	
	public CrearActividadDialog getCrearActividadDialog() {
		return this;
	}
	
	private void recolorFields() {
		for(int i = 0; i < pnRegistrarActividad.getComponentCount(); i++) {
			if(pnRegistrarActividad.getComponent(i) instanceof JTextField) {
				JTextField toRestore = (JTextField)pnRegistrarActividad.getComponent(i);
				toRestore.setBackground(Color.WHITE);
				toRestore.setForeground(Color.BLACK);
			}
		}
	}
	
	private void restablishFields() {
		for(int i = 0; i < pnRegistrarActividad.getComponentCount(); i++) {
			if(pnRegistrarActividad.getComponent(i) instanceof JTextField) {
				JTextField toRestore = (JTextField)pnRegistrarActividad.getComponent(i);
				toRestore.setText("");
			}
		}
	}
	
	private boolean checkEverything() {
		return checkCodigo() && checkNombre() && checkHoraInicio() && checkHoraFin() && checkLimitePlazas() && 
				checkIntensidad() && checkRecursos();
	}
	
	private boolean checkCodigo() { //restricciones del codigo de la actividad
		if(txtCodigo.getText().length() > LONGITUDMAX_CODIGO_ACTIVIDAD) {
			txtCodigo.setBackground(Color.RED);
			txtCodigo.setForeground(Color.WHITE);
			return false;
		}
		
		return true;
	}
	
	private boolean checkNombre() { //restricciones del nombre de la actividad
		if(txtNombre.getText().length() > LONGITUDMAX_NOMBRE_ACTIVIDAD) {
			txtNombre.setBackground(Color.RED);
			txtNombre.setForeground(Color.WHITE);
			return false;
		}
		
		return true;
	}
	
	private boolean checkHoraInicio() {
		if(txtInicio.getText().length() > 2) {
			txtInicio.setBackground(Color.RED);
			txtInicio.setForeground(Color.WHITE);
			return false;
		}
		
		try {
			Integer.parseInt(txtInicio.getText());
		} catch(NumberFormatException e) {
			return false;
		}
		
		return true;
	}
	
	private boolean checkHoraFin() {
		if(txtFinal.getText().length() > 2) {
			txtFinal.setBackground(Color.RED);
			txtFinal.setForeground(Color.WHITE);
			return false;
		}
		
		try {
			Integer.parseInt(txtFinal.getText());
		} catch(NumberFormatException e) {
			return false;
		}
		
		return true;
	}
	
	private boolean checkLimitePlazas() {
		try {
			Integer.parseInt(txtLimite.getText());
		} catch(NumberFormatException e) {
			txtLimite.setBackground(Color.RED);
			txtLimite.setForeground(Color.WHITE);
			return false;
		}
		
		return true;
	}
	
	private boolean checkIntensidad() {
		
		if(txtIntensidad.getText().length() > 1) {
			txtIntensidad.setBackground(Color.RED);
			txtIntensidad.setForeground(Color.WHITE);
			return false;
		}
		
		int intensidad;
		try {
			intensidad = Integer.parseInt(txtIntensidad.getText());
		} catch(NumberFormatException e) {
			txtIntensidad.setBackground(Color.RED);
			txtIntensidad.setForeground(Color.WHITE);
			return false;
		}
		
		if(intensidad != Actividad.INTENSIDAD_BAJA && intensidad != Actividad.INTENSIDAD_MODERADA && intensidad != Actividad.INTENSIDAD_ALTA) {
			txtIntensidad.setBackground(Color.RED);
			txtIntensidad.setForeground(Color.WHITE);
			return false;
		}
		
		return true;
	}
	
	private boolean checkRecursos() { //IMPORTANTE hay que definir un parámetro de la longitud de la id para hacer una comprobación más.
		try {
			String rawRecursos = txtRecursos.getText();
			String[] idRecursos = rawRecursos.split(", ");
		} catch(Exception e) {
			txtRecursos.setBackground(Color.RED);
			txtRecursos.setForeground(Color.WHITE);
			return false;
		}
		
		return true;
	}
}
