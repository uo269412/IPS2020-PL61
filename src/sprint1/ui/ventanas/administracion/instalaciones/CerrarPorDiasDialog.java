package sprint1.ui.ventanas.administracion.instalaciones;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.alquileres.Alquiler;
import sprint1.business.dominio.centroDeportes.instalaciones.Instalacion;
import sprint1.business.dominio.clientes.Cliente;
import sprint1.business.dominio.clientes.Socio;
import sprint1.business.dominio.clientes.Tercero;
import sprint1.ui.ventanas.administracion.actividades.CalendarioDisponibilidadInstalacion;
import sprint1.ui.ventanas.administracion.alquileres.AdminAlquilaTerceroDialog;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class CerrarPorDiasDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel pnPlanificarCierre;
	private JPanel pnBotones;
	private JRadioButton rdbtnUnDia;
	private JRadioButton rdbtnVariosDias;
	private JPanel pnPlanificaciones;
	private JPanel pnButtons;
	private JButton btnCancelar;
	private JButton btnCerrar;
	private JLabel lblFecha;
	private JPanel pnTipoPlanificacion;
	private JPanel pnPlanificarUnDia;
	private JTextField txtFechaUnDia;
	private JPanel pnPlanificarVariosDías;

	private ButtonGroup bg = new ButtonGroup();
	private ButtonGroup bg2 = new ButtonGroup();
	private JPanel pnSeleccionInstalacion;
	private JLabel lblInstalacionCerrar;
	private JComboBox<Instalacion> cbInstalacion;
	private JButton btnVerDisponibilidad;

	private Programa p;
	private JPanel pnTextFields;
	private JTextField txtFechaInicio;
	private JTextField txtFechaFin;
	private JPanel pnRbRepeticion;
	private JRadioButton rdbtnDiariamente;
	private JRadioButton rdbtnSemanalmente;

	/**
	 * Create the dialog.
	 */
	public CerrarPorDiasDialog(Programa p) {
		this.p = p;
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(CerrarPorDiasDialog.class.getResource("/sprint1/ui/resources/titulo.png")));
		setTitle("Cerrar una instalaci\u00F3n temporalmente");
		setBounds(100, 100, 469, 270);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(getPnSeleccionInstalacion());
		contentPanel.add(getPnPlanificarCierre());
		contentPanel.add(getPnButtons());

	}

	private JPanel getPnPlanificarCierre() {
		if (pnPlanificarCierre == null) {
			pnPlanificarCierre = new JPanel();
			pnPlanificarCierre.setLayout(new BorderLayout(0, 0));
			pnPlanificarCierre.add(getPnBotones(), BorderLayout.NORTH);
			pnPlanificarCierre.add(getPnPlanificaciones(), BorderLayout.CENTER);
		}
		return pnPlanificarCierre;
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.add(getRdbtnUnDia());
			pnBotones.add(getRdbtnVariosDias());
		}
		return pnBotones;
	}

	private JRadioButton getRdbtnUnDia() {
		if (rdbtnUnDia == null) {
			rdbtnUnDia = new JRadioButton("Un d\u00EDa");
			rdbtnUnDia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					pnPlanificarVariosDías.setVisible(false);
					pnPlanificarUnDia.setVisible(true);
					lblFecha.setText("Fecha para cerrar un d\u00EDa (Formato HH/DD/AAAA):");
				}
			});
			rdbtnUnDia.setSelected(true);
			bg.add(rdbtnUnDia);
		}
		return rdbtnUnDia;
	}

	private JRadioButton getRdbtnVariosDias() {
		if (rdbtnVariosDias == null) {
			rdbtnVariosDias = new JRadioButton("Varios d\u00EDas");
			rdbtnVariosDias.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					pnPlanificarUnDia.setVisible(false);
					pnPlanificarVariosDías.setVisible(true);

					lblFecha.setText("Fecha inicio del cierre/Fecha fin del cierre (Formatos HH/DD/AAAA):");
				}
			});
			bg.add(rdbtnVariosDias);
		}
		return rdbtnVariosDias;
	}

	private JPanel getPnPlanificaciones() {
		if (pnPlanificaciones == null) {
			pnPlanificaciones = new JPanel();
			pnPlanificaciones.setLayout(new BorderLayout(0, 0));
			pnPlanificaciones.add(getLblFecha(), BorderLayout.NORTH);
			pnPlanificaciones.add(getPnTipoPlanificacion(), BorderLayout.CENTER);
		}
		return pnPlanificaciones;
	}

	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			pnButtons.add(getBtnCancelar());
			pnButtons.add(getBtnCerrar());
		}
		return pnButtons;
	}

	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setBackground(new Color(255, 99, 71));
			btnCancelar.setForeground(Color.WHITE);
		}
		return btnCancelar;
	}

	private JButton getBtnCerrar() {
		if (btnCerrar == null) {
			btnCerrar = new JButton("Cerrar");
			btnCerrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					if (rdbtnUnDia.isSelected()) {
						if (checkFecha(txtFechaUnDia)) {
							cierreUnDia();
						}
					} else {
						if (checkFecha(txtFechaInicio) && checkFecha(txtFechaFin)) {
							cierreVariosDias();
						}
					}
				}
			});
			btnCerrar.setBackground(new Color(60, 179, 113));
			btnCerrar.setForeground(new Color(255, 255, 255));
		}
		return btnCerrar;
	}

	private boolean checkFecha(JTextField componente) {
		try {
			String[] fecha = componente.getText().split("/");
			if (fecha.length != 3) {
				colorWrongTextField(componente);
				return false;
			}
		} catch (Exception e) {
			colorWrongTextField(componente);
			return false;
		}

		return true;
	}

	private void colorWrongTextField(JTextField txt) {
		txt.setBackground(Color.RED);
		txt.setForeground(Color.WHITE);
	}

	private void cierreUnDia() {
		String[] fecha = txtFechaUnDia.getText().split("/");

		int dia = Integer.parseInt(fecha[0]);
		int mes = Integer.parseInt(fecha[1]);
		int año = Integer.parseInt(fecha[2]);
		try {
			if (p.cierreInstalacionDia((Instalacion) cbInstalacion.getSelectedItem(), dia, mes, año)) {
				printClientesAfectadosPorCierre(p.clientesAfectadosPorCierreDia((Instalacion) cbInstalacion.getSelectedItem(), dia, mes, año));
			} else {
				JOptionPane.showMessageDialog(this, "La instalación ya estaba cerrada ese día");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Ha ocurrido un error registrando el cierre");
		}
	}

	private void cierreVariosDias() {
		Set<Cliente> clientesAfectados = new HashSet<>();
		List<String> errores = new ArrayList<>();

		String[] fecha = txtFechaInicio.getText().split("/");

		int dia = Integer.parseInt(fecha[0]);
		int mes = Integer.parseInt(fecha[1]);
		int año = Integer.parseInt(fecha[2]);

		int añoUltimaReserva = año;
		int mesUltimaReserva = mes;
		int diaUltimaReserva = dia;
		int diaFin = 0;
		int mesFin = 0;
		int añoFin = 0;
		if (!txtFechaFin.getText().equals("")) {
			diaFin = Integer.parseInt(txtFechaFin.getText().split("/")[0]);
			mesFin = Integer.parseInt(txtFechaFin.getText().split("/")[1]);
			añoFin = Integer.parseInt(txtFechaFin.getText().split("/")[2]);
		}

		int incremento = -1;
		switch (bg2.getSelection().getActionCommand()) {
		case "diariamente":
			incremento = 1;
			break;
		case "semanalmente":
			incremento = 7;
			break;
		}

		while (LocalDate.of(añoUltimaReserva, mesUltimaReserva, diaUltimaReserva)
				.isBefore(LocalDate.of(añoFin, mesFin, diaFin)) || LocalDate.of(añoUltimaReserva, mesUltimaReserva, diaUltimaReserva)
				.isEqual(LocalDate.of(añoFin, mesFin, diaFin))) {
			
			if(mesUltimaReserva <= 7) {
				if (mesUltimaReserva % 2 == 1) {
					if (diaUltimaReserva > 30) {
						mesUltimaReserva++;
						diaUltimaReserva -= 30;
						if (mesUltimaReserva > 12) {
							mesUltimaReserva -= 12;
							añoUltimaReserva++;
						}
					}
				} else {
					if (diaUltimaReserva > 29) {
						mesUltimaReserva++;
						diaUltimaReserva -= 29;
						if (mesUltimaReserva > 12) {
							mesUltimaReserva -= 12;
							añoUltimaReserva++;
						}
					}
				}
			} else {
				if (mesUltimaReserva % 2 == 1 || mesUltimaReserva == 12) {
					if (diaUltimaReserva > 29) {
						mesUltimaReserva++;
						diaUltimaReserva -= 29;
						if (mesUltimaReserva > 12) {
							mesUltimaReserva -= 12;
							añoUltimaReserva++;
						}
					}
				} else {
					if (diaUltimaReserva > 30) {
						mesUltimaReserva++;
						diaUltimaReserva -= 30;
						if (mesUltimaReserva > 12) {
							mesUltimaReserva -= 12;
							añoUltimaReserva++;
						}
					}
				}
			}
			
			try {
				if (!p.cierreInstalacionDia((Instalacion) cbInstalacion.getSelectedItem(), diaUltimaReserva,
						mesUltimaReserva, añoUltimaReserva)) {
					String msg = diaUltimaReserva + "/" + mesUltimaReserva + "/" + añoUltimaReserva;
					errores.add(msg);
				} else {
					clientesAfectados.addAll(p.clientesAfectadosPorCierreDia((Instalacion) cbInstalacion.getSelectedItem(), diaUltimaReserva,
						mesUltimaReserva, añoUltimaReserva));
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Ha ocurrido un error con la base de datos tratando de cerrar la instalación");
			}

			diaUltimaReserva += incremento;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("La instalación ya estaba cerrada los días:\n");
		for (String s : errores) {
			sb.append("\t" + s + "\n");
		}
		
		printClientesAfectadosPorCierre(clientesAfectados);
		
		if(errores.size() > 0)
			JOptionPane.showMessageDialog(this, sb.toString());
	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha para cerrar un d\u00EDa (Formato HH/DD/AAAA):");
		}
		return lblFecha;
	}

	private JPanel getPnTipoPlanificacion() {
		if (pnTipoPlanificacion == null) {
			pnTipoPlanificacion = new JPanel();
			pnTipoPlanificacion.setLayout(new BoxLayout(pnTipoPlanificacion, BoxLayout.Y_AXIS));
			pnTipoPlanificacion.add(getPnPlanificarUnDia());
			pnTipoPlanificacion.add(getPnPlanificarVariosDías());
		}
		return pnTipoPlanificacion;
	}

	private JPanel getPnPlanificarUnDia() {
		if (pnPlanificarUnDia == null) {
			pnPlanificarUnDia = new JPanel();
			pnPlanificarUnDia.add(getTxtFechaUnDia());
		}
		return pnPlanificarUnDia;
	}

	private JTextField getTxtFechaUnDia() {
		if (txtFechaUnDia == null) {
			txtFechaUnDia = new JTextField();
			txtFechaUnDia.setColumns(10);
		}
		return txtFechaUnDia;
	}

	private JPanel getPnPlanificarVariosDías() {
		if (pnPlanificarVariosDías == null) {
			pnPlanificarVariosDías = new JPanel();
			pnPlanificarVariosDías.setLayout(new BorderLayout(0, 0));
			pnPlanificarVariosDías.add(getPnTextFields());
			pnPlanificarVariosDías.add(getPnRbRepeticion(), BorderLayout.SOUTH);
			pnPlanificarVariosDías.setVisible(false);
		}
		return pnPlanificarVariosDías;
	}

	private JPanel getPnSeleccionInstalacion() {
		if (pnSeleccionInstalacion == null) {
			pnSeleccionInstalacion = new JPanel();
			pnSeleccionInstalacion.setLayout(new BorderLayout(0, 0));
			pnSeleccionInstalacion.add(getLblInstalacionCerrar(), BorderLayout.NORTH);
			pnSeleccionInstalacion.add(getCbInstalacion(), BorderLayout.CENTER);
			pnSeleccionInstalacion.add(getBtnVerDisponibilidad(), BorderLayout.SOUTH);
		}
		return pnSeleccionInstalacion;
	}

	private JLabel getLblInstalacionCerrar() {
		if (lblInstalacionCerrar == null) {
			lblInstalacionCerrar = new JLabel("Instalaci\u00F3n a cerrar:");
		}
		return lblInstalacionCerrar;
	}

	private JComboBox<Instalacion> getCbInstalacion() {
		if (cbInstalacion == null) {
			cbInstalacion = new JComboBox<>();
			cbInstalacion.setModel(new DefaultComboBoxModel<Instalacion>(
					p.getInstalacionesDisponibles().toArray(new Instalacion[p.getInstalacionesDisponibles().size()])));
		}
		return cbInstalacion;
	}

	private JButton getBtnVerDisponibilidad() {
		if (btnVerDisponibilidad == null) {
			btnVerDisponibilidad = new JButton("Ver disponibilidad de instalaciones");
			btnVerDisponibilidad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CalendarioDisponibilidadInstalacion cdi = new CalendarioDisponibilidadInstalacion(getPrograma());
					cdi.setModal(true);
					cdi.setVisible(true);
					cdi.setLocationRelativeTo(CerrarPorDiasDialog.this);
				}
			});
			btnVerDisponibilidad.setForeground(new Color(255, 255, 255));
			btnVerDisponibilidad.setBackground(new Color(30, 144, 255));
		}
		return btnVerDisponibilidad;
	}

	private Programa getPrograma() {
		return this.p;
	}

	private JPanel getPnTextFields() {
		if (pnTextFields == null) {
			pnTextFields = new JPanel();
			pnTextFields.add(getTxtFechaInicio());
			pnTextFields.add(getTxtFechaFin());
		}
		return pnTextFields;
	}

	private JTextField getTxtFechaInicio() {
		if (txtFechaInicio == null) {
			txtFechaInicio = new JTextField();
			txtFechaInicio.setColumns(10);
		}
		return txtFechaInicio;
	}

	private JTextField getTxtFechaFin() {
		if (txtFechaFin == null) {
			txtFechaFin = new JTextField();
			txtFechaFin.setColumns(10);
		}
		return txtFechaFin;
	}

	private JPanel getPnRbRepeticion() {
		if (pnRbRepeticion == null) {
			pnRbRepeticion = new JPanel();
			pnRbRepeticion.add(getRdbtnDiariamente());
			pnRbRepeticion.add(getRdbtnSemanalmente());
		}
		return pnRbRepeticion;
	}

	private JRadioButton getRdbtnDiariamente() {
		if (rdbtnDiariamente == null) {
			rdbtnDiariamente = new JRadioButton("Diariamente");
			rdbtnDiariamente.setSelected(true);
			bg2.add(rdbtnDiariamente);
			rdbtnDiariamente.setActionCommand("diariamente");
		}
		return rdbtnDiariamente;
	}

	private JRadioButton getRdbtnSemanalmente() {
		if (rdbtnSemanalmente == null) {
			rdbtnSemanalmente = new JRadioButton("Semanalmente");
			bg2.add(rdbtnSemanalmente);
			rdbtnSemanalmente.setActionCommand("semanalmente");
		}
		return rdbtnSemanalmente;
	}

	private void printClientesAfectadosPorCierre(Set<Cliente> clientesAfectados) {
		StringBuilder sb = new StringBuilder();
		sb.append("-------CLIENTES AFECTADOS POR EL CIERRE DE LA INSTALACIÓN "
				+ ((Instalacion) cbInstalacion.getSelectedItem()).getNombre() + "-------\n");
		for (Cliente c : clientesAfectados) {
			Date date = new Date();
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(date);
			if (c instanceof Tercero) {
				sb.append("Tercero: " + c.getId_cliente() + " Nombre: ");
				sb.append(((Tercero) c).getNombre() + "\n");
			} else if (c instanceof Socio) {
				sb.append("Socio: " + c.getId_cliente() + " Nombre: ");
				sb.append(((Socio) c).getNombre() + "\n");
			}

		}

		System.out.println(sb.toString());
	}
}
