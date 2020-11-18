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
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;

import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Instalacion;
import sprint1.business.clases.Programa;
import sprint1.business.clases.Recurso;

import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import sprint1.business.clases.Actividad;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;

public class AsignarActividadVariosDiasDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel pnProgramarActividad;
	private JPanel pnDias;
	private JButton btnAñadir;
	private JPanel pnBotones;
	private JButton btnVolver;
	private JPanel pnSemana;
	private JLabel lblDias;
	private JPanel pnPrimera;
	private JPanel pnSegunda;
	private JPanel pnLunes;
	private JCheckBox chckbxLunes;
	private JPanel pnMartes;
	private JCheckBox chckbxMartes;
	private JPanel pnMiercoles;
	private JCheckBox chckbxMiercoles;
	private JPanel pnJueves;
	private JCheckBox chckbxJueves;
	private JPanel pnViernes;
	private JCheckBox chckbxViernes;
	private JPanel pnSabado;
	private JCheckBox chckbxSabado;
	private JPanel pnDomingo;
	private JCheckBox chckbxDomingo;
	private JPanel pnActividad;
	private JLabel lblActividad;

	private JPanel pnInstalacion;
	private JLabel lblInstalacion;
	private JComboBox<Actividad> cmbActividades;
	private JComboBox<Instalacion> cmbInstalaciones;
	private JPanel pnFechaFin;
	private JLabel lblFechaFin;
	private JTextField txtFechaFin;

	private Programa programa;
	private int dia;
	private int mes;
	private int año;
	private int horaInicio;

	DefaultComboBoxModel<Actividad> modeloActividades = new DefaultComboBoxModel<>();
	private DefaultComboBoxModel<Instalacion> modeloInstalaciones = new DefaultComboBoxModel<>();

	/**
	 * Create the dialog.
	 */
	public AsignarActividadVariosDiasDialog(CalendarioSemanalPlanificar parent, Programa p, int dia, int mes, int año,
			int horaInicio) {
		setTitle("Centro de deportes: Planificando actividades");
		this.programa = p;
		this.dia = dia;
		this.mes = mes;
		this.año = año;
		setBounds(100, 100, 455, 569);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		contentPanel.add(getPnProgramarActividad());
		getContentPane().add(getPnBotones(), BorderLayout.SOUTH);
		cargarActividades();
		cargarInstalaciones();
	}

	private void cargarActividades() {
		for (Actividad actividad : getPrograma().getActividades()) {
			modeloActividades.addElement(actividad);
		}
	}

	private void cargarInstalaciones() {
		for (Instalacion instalacion : getPrograma().getInstalaciones()) {
			if (instalacion.getEstado() == Instalacion.DISPONIBLE) {
				modeloInstalaciones.addElement(instalacion);
			}
		}
	}

	private Programa getPrograma() {
		return this.programa;
	}

	private JPanel getPnProgramarActividad() {
		if (pnProgramarActividad == null) {
			pnProgramarActividad = new JPanel();
			pnProgramarActividad.setLayout(new GridLayout(0, 1, 0, 0));
			pnProgramarActividad.add(getPnActividad());
			pnProgramarActividad.add(getPnInstalacion());
			pnProgramarActividad.add(getPnDias());
			pnProgramarActividad.add(getPnFechaFin());
		}
		return pnProgramarActividad;
	}

	private JPanel getPnDias() {
		if (pnDias == null) {
			pnDias = new JPanel();
			pnDias.setLayout(new GridLayout(0, 1, 0, 0));
			pnDias.add(getLblDias());
			pnDias.add(getPnSemana());
		}
		return pnDias;
	}

	private JButton getBtnAñadir() {
		if (btnAñadir == null) {
			btnAñadir = new JButton("Planificar");
			if (cmbInstalaciones.getModel().getSize() == 0) {
				btnAñadir.setEnabled(false);
			} else {
				btnAñadir.setEnabled(true);
			}
			btnAñadir.setForeground(Color.WHITE);
			btnAñadir.setBackground(new Color(60, 179, 113));
		}
		return btnAñadir;
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
			pnBotones.add(getBtnVolver());
			pnBotones.add(getBtnAñadir());
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
			btnVolver.setBackground(new Color(240, 240, 240));
			btnVolver.setForeground(Color.BLACK);
			btnVolver.setActionCommand("Cancel");
		}
		return btnVolver;
	}

	private AsignarActividadVariosDiasDialog getMe() {
		return this;
	}

	private int getMinRecursos() {
		int minRecursos = Integer.MAX_VALUE;
		for (Recurso r : ((Actividad) cmbActividades.getSelectedItem()).getRecursos()) {
			if (r.getUnidades() < minRecursos) {
				minRecursos = r.getUnidades();
			}
		}

		return minRecursos;
	}

	private ActividadPlanificada crearPlanificada() {
//		String codigoAAsignar = ((Actividad) cmbActividades.getSelectedItem()).getCodigo();
//		int limitePlazas;
//		if(txtLimitePlazas.getText().equals("")){
//			limitePlazas = Integer.MAX_VALUE;
//		}
//		else {
//			limitePlazas = Integer.parseInt(txtLimitePlazas.getText());
//		}
//		int horaInicio = Integer.parseInt(txtHoraInicio.getText());
//		int horaFin = Integer.parseInt(txtHoraFin.getText());
//		String codigoInstalacion = ((Instalacion)cmbInstalaciones.getSelectedItem()).getCodigoInstalacion();
//		return new ActividadPlanificada(codigoAAsignar, dia, mes, año, limitePlazas, horaInicio, horaFin, codigoInstalacion);
		return null;
	}

	private void rellenarInstalacion() {
		cmbInstalaciones.setEnabled(true);
		Actividad a = programa.getActividades().get(cmbActividades.getSelectedIndex());
		if (a.requiresRecursos()) {
			boolean todosIguales = true;
			String i = a.getRecursos().get(0).getInstalacion();
			for (Recurso r : a.getRecursos()) {
				if (r.getInstalacion().equals(i)) {
					todosIguales = true;
				} else {
					todosIguales = false;
					break;
				}
			}
			if (todosIguales) {
				try {
					Instalacion inst = programa.obtenerInstalacionPorId(a.getRecursos().get(0).getInstalacion());
					Instalacion[] arrayInst = new Instalacion[1];
					arrayInst[0] = inst;
					cmbInstalaciones.setModel(new DefaultComboBoxModel<Instalacion>(arrayInst));
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(this,
							"Ha habido un problema con la base de datos asignando la instalacion"
									+ ", póngase en contacto con el desarrollador");
				}
			} else {
				cmbInstalaciones.setEnabled(false);
				cmbInstalaciones.setToolTipText("No hay ninguna instalación que tenga recursos para esta actividad");
				btnAñadir.setEnabled(false);
			}
		} else {
			cmbInstalaciones.setModel(new DefaultComboBoxModel<Instalacion>(programa.getInstalacionesDisponibles()
					.toArray(new Instalacion[programa.getInstalacionesDisponibles().size()])));
		}
	}

	private void adaptarActividadesAInstalacion() {
		Instalacion i = (Instalacion) cmbInstalaciones.getSelectedItem();
		cmbActividades.setModel(new DefaultComboBoxModel<Actividad>(programa.actividadesPorInstalacion(i.getCodigo())
				.toArray(new Actividad[programa.actividadesPorInstalacion(i.getCodigo()).size()])));
	}

	private class ComparePlanificadas implements Comparator<ActividadPlanificada> {

		@Override
		public int compare(ActividadPlanificada arg0, ActividadPlanificada arg1) {
			return arg0.compareTo(arg1);
		}

	}

	private JPanel getPnSemana() {
		if (pnSemana == null) {
			pnSemana = new JPanel();
			pnSemana.setLayout(new GridLayout(0, 1, 0, 0));
			pnSemana.add(getPnPrimera());
			pnSemana.add(getPnSegunda());
		}
		return pnSemana;
	}

	private JLabel getLblDias() {
		if (lblDias == null) {
			lblDias = new JLabel("D\u00EDas:");
			lblDias.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblDias;
	}

	private JPanel getPnPrimera() {
		if (pnPrimera == null) {
			pnPrimera = new JPanel();
			pnPrimera.setLayout(new GridLayout(1, 0, 0, 0));
			pnPrimera.add(getPnLunes_1());
			pnPrimera.add(getPnMartes_1());
			pnPrimera.add(getPnMiercoles_1());
			pnPrimera.add(getPnJueves_1());
		}
		return pnPrimera;
	}

	private JPanel getPnSegunda() {
		if (pnSegunda == null) {
			pnSegunda = new JPanel();
			pnSegunda.setLayout(new GridLayout(1, 0, 0, 0));
			pnSegunda.add(getPnViernes_1());
			pnSegunda.add(getPnSabado_1());
			pnSegunda.add(getPnDomingo_1());
		}
		return pnSegunda;
	}

	private JPanel getPnLunes_1() {
		if (pnLunes == null) {
			pnLunes = new JPanel();
			pnLunes.add(getChckbxLunes_1());
		}
		return pnLunes;
	}

	private JCheckBox getChckbxLunes_1() {
		if (chckbxLunes == null) {
			chckbxLunes = new JCheckBox("L");
		}
		return chckbxLunes;
	}

	private JPanel getPnMartes_1() {
		if (pnMartes == null) {
			pnMartes = new JPanel();
			pnMartes.add(getChckbxMartes_1());
		}
		return pnMartes;
	}

	private JCheckBox getChckbxMartes_1() {
		if (chckbxMartes == null) {
			chckbxMartes = new JCheckBox("M");
		}
		return chckbxMartes;
	}

	private JPanel getPnMiercoles_1() {
		if (pnMiercoles == null) {
			pnMiercoles = new JPanel();
			pnMiercoles.add(getChckbxMiercoles_1());
		}
		return pnMiercoles;
	}

	private JCheckBox getChckbxMiercoles_1() {
		if (chckbxMiercoles == null) {
			chckbxMiercoles = new JCheckBox("X");
		}
		return chckbxMiercoles;
	}

	private JPanel getPnJueves_1() {
		if (pnJueves == null) {
			pnJueves = new JPanel();
			pnJueves.add(getChckbxJueves_1());
		}
		return pnJueves;
	}

	private JCheckBox getChckbxJueves_1() {
		if (chckbxJueves == null) {
			chckbxJueves = new JCheckBox("J");
		}
		return chckbxJueves;
	}

	private JPanel getPnViernes_1() {
		if (pnViernes == null) {
			pnViernes = new JPanel();
			pnViernes.add(getChckbxViernes_1());
		}
		return pnViernes;
	}

	private JCheckBox getChckbxViernes_1() {
		if (chckbxViernes == null) {
			chckbxViernes = new JCheckBox("V");
		}
		return chckbxViernes;
	}

	private JPanel getPnSabado_1() {
		if (pnSabado == null) {
			pnSabado = new JPanel();
			pnSabado.add(getChckbxSabado_1());
		}
		return pnSabado;
	}

	private JCheckBox getChckbxSabado_1() {
		if (chckbxSabado == null) {
			chckbxSabado = new JCheckBox("S");
		}
		return chckbxSabado;
	}

	private JPanel getPnDomingo_1() {
		if (pnDomingo == null) {
			pnDomingo = new JPanel();
			pnDomingo.add(getChckbxDomingo_1());
		}
		return pnDomingo;
	}

	private JCheckBox getChckbxDomingo_1() {
		if (chckbxDomingo == null) {
			chckbxDomingo = new JCheckBox("D");
		}
		return chckbxDomingo;
	}

	private JPanel getPnActividad() {
		if (pnActividad == null) {
			pnActividad = new JPanel();
			pnActividad.setLayout(new GridLayout(0, 1, 0, 0));
			pnActividad.add(getLblActividad_1());
			pnActividad.add(getCmbActividades_1());
		}
		return pnActividad;
	}

	private JLabel getLblActividad_1() {
		if (lblActividad == null) {
			lblActividad = new JLabel("Actividad a asignar:");
			lblActividad.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblActividad;
	}

	private JComboBox<Actividad> getCmbActividades_1() {
		if (cmbActividades == null) {
			cmbActividades = new JComboBox<Actividad>(modeloActividades);
			cmbActividades.setFont(new Font("Tahoma", Font.PLAIN, 8));
		}
		return cmbActividades;
	}

	private JPanel getPnInstalacion() {
		if (pnInstalacion == null) {
			pnInstalacion = new JPanel();
			pnInstalacion.setLayout(new GridLayout(0, 1, 0, 0));
			pnInstalacion.add(getLblInstalacion_1());
			pnInstalacion.add(getCmbInstalaciones_1());
		}
		return pnInstalacion;
	}

	private JLabel getLblInstalacion_1() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalaci\u00F3n:");
			lblInstalacion.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblInstalacion;
	}

	private JComboBox<Instalacion> getCmbInstalaciones_1() {
		if (cmbInstalaciones == null) {
			cmbInstalaciones = new JComboBox<Instalacion>(modeloInstalaciones);
			cmbInstalaciones.setEnabled(true);
		}
		return cmbInstalaciones;
	}

	private JPanel getPnFechaFin() {
		if (pnFechaFin == null) {
			pnFechaFin = new JPanel();
			pnFechaFin.setLayout(new GridLayout(0, 1, 0, 0));
			pnFechaFin.add(getLblFechaFin());
			pnFechaFin.add(getTxtFechaFin());
		}
		return pnFechaFin;
	}

	private JLabel getLblFechaFin() {
		if (lblFechaFin == null) {
			lblFechaFin = new JLabel("Fecha fin:");
			lblFechaFin.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblFechaFin;
	}

	private JTextField getTxtFechaFin() {
		if (txtFechaFin == null) {
			txtFechaFin = new JTextField();
			txtFechaFin.setColumns(10);
		}
		return txtFechaFin;
	}
}