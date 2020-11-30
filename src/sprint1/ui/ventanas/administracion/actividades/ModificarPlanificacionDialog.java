package sprint1.ui.ventanas.administracion.actividades;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.actividades.Actividad;
import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.business.dominio.centroDeportes.instalaciones.Instalacion;
import sprint1.business.dominio.centroDeportes.instalaciones.Recurso;
import sprint1.business.dominio.clientes.Socio;
import sprint1.ui.ventanas.administracion.util.CalendarioSemanalModificar;

public class ModificarPlanificacionDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblInstalacion;
	private JComboBox<Instalacion> cbInstalacion;
	private JLabel lblFechaHora;
	private JPanel pnFechaDesarrollo;
	private JLabel lblHoraInicio;
	private JLabel lblHoraFin;
	private JLabel lblFecha;
	private JTextField txtHoraInicio;
	private JTextField txtHoraFin;
	private JTextField txtFecha;
	private JPanel pnBotones;
	private JButton btnModificar;
	private JButton btnCancelar;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JLabel lblCodigo;
	private JTextField txtCodigo;
	
	private CalendarioSemanalModificar parent;
	private Programa p;
	private ActividadPlanificada a;
	/**
	 * Create the dialog.
	 */
	public ModificarPlanificacionDialog(CalendarioSemanalModificar parent, ActividadPlanificada a) {
		this.parent = parent;
		this.p = parent.getPrograma();
		this.a = a;
		
		
		setTitle("Modificar planificaci\u00F3n de actividad");
		setBounds(100, 100, 367, 421);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0,1,0,0));
		contentPanel.add(getLblCodigo());
		contentPanel.add(getTextFieldCodigo());
		contentPanel.add(getLblNombre());
		contentPanel.add(getTextFieldNombre());
		contentPanel.add(getLblInstalacion());
		contentPanel.add(getCbInstalacion());
		contentPanel.add(getLblFechaHora());
		contentPanel.add(getPnFechaDesarrollo());
		contentPanel.add(getPnBotones());
		rellenarPredeterminados();
	}

	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalaci\u00F3n:");
		}
		return lblInstalacion;
	}
	private JComboBox<Instalacion> getCbInstalacion() {
		if (cbInstalacion == null) {
			cbInstalacion = new JComboBox<>();
			rellenarInstalacion();
		}
		return cbInstalacion;
	}
	private JLabel getLblFechaHora() {
		if (lblFechaHora == null) {
			lblFechaHora = new JLabel("Fecha y hora de desarrollo:");
		}
		return lblFechaHora;
	}
	private JPanel getPnFechaDesarrollo() {
		if (pnFechaDesarrollo == null) {
			pnFechaDesarrollo = new JPanel();
			pnFechaDesarrollo.setLayout(new GridLayout(2,3,0,0));
			pnFechaDesarrollo.add(getLblHoraInicio());
			pnFechaDesarrollo.add(getLabel_1());
			pnFechaDesarrollo.add(getLabel_2());
			pnFechaDesarrollo.add(getTextField_1());
			pnFechaDesarrollo.add(getTextField_2());
			pnFechaDesarrollo.add(getTextField_3());
		}
		return pnFechaDesarrollo;
	}
	private JLabel getLblHoraInicio() {
		if (lblHoraInicio == null) {
			lblHoraInicio = new JLabel("Hora de inicio:");
		}
		return lblHoraInicio;
	}
	private JLabel getLabel_1() {
		if (lblHoraFin == null) {
			lblHoraFin = new JLabel("Hora de fin:");
		}
		return lblHoraFin;
	}
	private JLabel getLabel_2() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha (DD/MM/YYYY):");
		}
		return lblFecha;
	}
	private JTextField getTextField_1() {
		if (txtHoraInicio == null) {
			txtHoraInicio = new JTextField();
			txtHoraInicio.setColumns(10);
		}
		return txtHoraInicio;
	}
	private JTextField getTextField_2() {
		if (txtHoraFin == null) {
			txtHoraFin = new JTextField();
			txtHoraFin.setColumns(10);
		}
		return txtHoraFin;
	}
	private JTextField getTextField_3() {
		if (txtFecha == null) {
			txtFecha = new JTextField();
			txtFecha.setColumns(10);
		}
		return txtFecha;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.add(getBtnModificar());
			pnBotones.add(getBtnCancelar());
		}
		return pnBotones;
	}
	private JButton getBtnModificar() {
		if (btnModificar == null) {
			btnModificar = new JButton("Modificar");
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(checkFecha() && checkHoraFin() && checkHoraInicio()) {
						String fecha[] = txtFecha.getText().split("/");
						a.setCodigoInstalacion(((Instalacion)cbInstalacion.getSelectedItem()).getCodigo());
						a.setDia(Integer.parseInt(fecha[0]));
						a.setMes(Integer.parseInt(fecha[1]));
						a.setAño(Integer.parseInt(fecha[2]));
						a.setHoraInicio(Integer.parseInt(txtHoraInicio.getText()));
						a.setHoraFin(Integer.parseInt(txtHoraFin.getText()));
						try {
							
							p.updateActividadPlanificada(a);
							printSociosAfectados();
							dispose();
							getThisParent().generarPaneles();
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(ModificarPlanificacionDialog.this, 
									"Ha ocurrido un error actualizando la planificación de la actividad");
						}
					}
					
					
				}
			});
			btnModificar.setBackground(new Color(60, 179, 113));
			btnModificar.setForeground(Color.WHITE);
		}
		return btnModificar;
	}
	
	private CalendarioSemanalModificar getThisParent() {
		return this.parent;
	}
	
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnCancelar.setBackground(new Color(255, 99, 71));
			btnCancelar.setForeground(new Color(255, 255, 255));
		}
		return btnCancelar;
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre de la actividad:");
		}
		return lblNombre;
	}
	private JTextField getTextFieldNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setEditable(false);
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}
	private JLabel getLblCodigo() {
		if (lblCodigo == null) {
			lblCodigo = new JLabel("C\u00F3digo de la actividad planificada:");
		}
		return lblCodigo;
	}
	private JTextField getTextFieldCodigo() {
		if (txtCodigo == null) {
			txtCodigo = new JTextField();
			txtCodigo.setEditable(false);
			txtCodigo.setColumns(10);
		}
		return txtCodigo;
	}
	
	private void rellenarPredeterminados() {
		this.txtCodigo.setText(this.a.getCodigoPlanificada());
		this.txtNombre.setText(p.encontrarNombrePlanificada(a));
		this.txtHoraInicio.setText(Integer.toString(this.a.getHoraInicio()));
		this.txtHoraFin.setText(Integer.toString(this.a.getHoraFin()));
		String fecha = Integer.toString(a.getDia()) + "/" + Integer.toString(a.getMes()) + "/" + Integer.toString(a.getAño());
		this.txtFecha.setText(fecha);
		
		DefaultComboBoxModel<Instalacion> comboModel = (DefaultComboBoxModel<Instalacion>) cbInstalacion.getModel();
		for(int i = 0;  i < comboModel.getSize(); i++) {
			if(comboModel.getElementAt(i).getCodigo().equals(a.getCodigoInstalacion())) {
				cbInstalacion.setSelectedItem(comboModel.getElementAt(i));
				break;
			}
		}
	}
	
	private boolean checkHoraInicio() {
		if (txtHoraInicio.getText().length() > 2) {
			colorWrongTextField(txtHoraInicio);
			return false;
		}

		try {
			Integer.parseInt(txtHoraInicio.getText());
		} catch (NumberFormatException e) {
			colorWrongTextField(txtHoraInicio);
			return false;
		}

		return true;
	}

	private boolean checkHoraFin() {
		if (txtHoraFin.getText().length() > 2) {
			colorWrongTextField(txtHoraFin);
			return false;
		}

		try {
			Integer.parseInt(txtHoraFin.getText());
		} catch (NumberFormatException e) {
			colorWrongTextField(txtHoraFin);
			return false;
		}

		return true;
	}
	
	private boolean checkFecha() {
		try {
			String[] fecha = txtFecha.getText().split("/");
			if(fecha.length != 3) {
				colorWrongTextField(txtFecha);
				return false;
			}
		} catch(Exception e) {
			colorWrongTextField(txtFecha);
			return false;
		}
		
		return true;
	}
	
	private void colorWrongTextField(JTextField txt) {
		txt.setBackground(Color.RED);
		txt.setForeground(Color.WHITE);
	}
	
	private void printSociosAfectados()  {
		StringBuilder sb = new StringBuilder();
		sb.append("--------SOCIOS AFECTADOS POR MODIFICACIÓN------");
		try {
			for(Socio s: p.sociosAfectadosPorModificacionActividad(a)) {
				sb.append("\tSocio: " + s.getNombre() + " " + s.getApellido() + ", con id " + s.getId_cliente() + "\n");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Ha ocurrido un problema tratando de recuperar los socios afectados por la modificación");
		}
	}
	
	private void rellenarInstalacion() {
		Actividad act = p.encontrarActividad(a.getCodigoActividad());
		if(act.requiresRecursos()) {
			boolean todosIguales = true;
			String i = act.getRecursos().get(0).getInstalacion();
			for(Recurso r: act.getRecursos()) {
				if(r.getInstalacion().equals(i)) {
					todosIguales = true;
				} else {
					todosIguales = false;
					break;
				}
			}
			if(todosIguales) {
				try {
					Instalacion inst = p.obtenerInstalacionPorId(act.getRecursos().get(0).getInstalacion());
					Instalacion[] arrayInst = new Instalacion[1];
					arrayInst[0] = inst;
					cbInstalacion.setModel(new DefaultComboBoxModel<Instalacion>(arrayInst));
				} catch(SQLException e) {
					JOptionPane.showMessageDialog(this, "Ha habido un problema con la base de datos asignando la instalacion"
							+ ", póngase en contacto con el desarrollador");
				}
			} else {
				cbInstalacion.setEnabled(false);
				cbInstalacion.setToolTipText("No hay ninguna instalación que tenga recursos para mover esta actividad planificada");
				btnModificar.setEnabled(false);
			}
		} else {
			try {

				Set<Instalacion> set = p.instalacionesDisponiblesParaActividad(p.encontrarActividad(a.getCodigoActividad()));
				List<Instalacion> lista = new LinkedList<>();
				for(Instalacion i: set) {
					lista.add(i);
				}
				cbInstalacion.setModel(new DefaultComboBoxModel<Instalacion>(lista.toArray(new Instalacion[lista.size()])));
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Ha ocurrido un error encontrando las instalaciones disponibles para la actividad");
				dispose();
			}
			
		}
	}
}
