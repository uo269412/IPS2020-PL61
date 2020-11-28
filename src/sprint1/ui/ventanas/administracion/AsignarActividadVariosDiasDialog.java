package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import sprint1.business.clases.Actividad;
import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Conflicto;
import sprint1.business.clases.Instalacion;
import sprint1.business.clases.Programa;

public class AsignarActividadVariosDiasDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel pnProgramarActividad;
	private JButton btnAñadir;
	private JPanel pnBotones;
	private JButton btnVolver;
	private JPanel pnInfo;

	private JPanel pnInstalacion;

	private Programa programa;
	private int dia;
	private int mes;
	private int año;
	private int horaInicio;
	private CalendarioSemanalPlanificar parent;

	DefaultComboBoxModel<Actividad> modeloActividades = new DefaultComboBoxModel<>();
	private DefaultComboBoxModel<Instalacion> modeloInstalaciones = new DefaultComboBoxModel<>();
	private DefaultComboBoxModel<Instalacion> modeloInstalacionesLunes = new DefaultComboBoxModel<>();
	private DefaultComboBoxModel<Instalacion> modeloInstalacionesMartes = new DefaultComboBoxModel<>();
	private DefaultComboBoxModel<Instalacion> modeloInstalacionesMiercoles = new DefaultComboBoxModel<>();
	private DefaultComboBoxModel<Instalacion> modeloInstalacionesJueves = new DefaultComboBoxModel<>();
	private DefaultComboBoxModel<Instalacion> modeloInstalacionesViernes = new DefaultComboBoxModel<>();
	private DefaultComboBoxModel<Instalacion> modeloInstalacionesSabado = new DefaultComboBoxModel<>();
	private DefaultComboBoxModel<Instalacion> modeloInstalacionesDomingo = new DefaultComboBoxModel<>();
	private JPanel pnHoraFin;
	private JPanel pnDecisionesInstalaciones;
	private JPanel pnUnaSolaInstalación;
	private JPanel pnVariasInstalaciones;
	private JRadioButton rdbtnUnaInstalacion;
	private JRadioButton rdbtnVariasInstalaciones;
	private JComboBox<Instalacion> cmbInstalaciones;
	private JPanel pnDias;
	private JPanel pnSemana;
	private JPanel pnPrimera;
	private JPanel pnLunes;
	private JCheckBox chckbxLunes;
	private JPanel pnMartes;
	private JCheckBox chckbxMartes;
	private JPanel pnMiercoles;
	private JCheckBox chckbxMiercoles;
	private JPanel pnJueves;
	private JCheckBox chckbxJueves;
	private JPanel pnSegunda;
	private JPanel pnViernes;
	private JCheckBox chckbxViernes;
	private JPanel pnSabado;
	private JCheckBox chckbxSabado;
	private JPanel pnDomingo;
	private JCheckBox chckbxDomingo;
	private JPanel pnModificarLunes;
	private JLabel lblInstalacionLunes;
	private JComboBox<Instalacion> cmbInstalacionesLunes;
	private JLabel lblHoraFinLunes;
	private JTextField txtHoraFinLunes;
	private JPanel pnActividad;
	private JComboBox<Actividad> cmbActividades;
	private JPanel pnFechaFin;
	private JTextField txtFechaFin;
	private JPanel pnModificarMartes;
	private JLabel lblInstalacionMartes;
	private JComboBox<Instalacion> cmbInstalacionesMartes;
	private JLabel lblHoraFinMartes;
	private JTextField txtHoraFinMartes;
	private JPanel pnModificarMiercoles;
	private JLabel lblInstalacionMiercoles;
	private JComboBox<Instalacion> cmbInstalacionesMiercoles;
	private JLabel lblHoraFinMiercoles;
	private JTextField txtHoraFinMiercoles;
	private JPanel pnModificarJueves;
	private JLabel lblInstalacionJueves;
	private JComboBox<Instalacion> cmbInstalacionesJueves;
	private JLabel lblHoraFinJueves;
	private JTextField txtHoraFinJueves;
	private JPanel pnModificarViernes;
	private JLabel lblInstalacionViernes;
	private JComboBox<Instalacion> cmbInstalacionesViernes;
	private JLabel lblHoraFinViernes;
	private JTextField txtHoraFinViernes;
	private JPanel pnModificarSabado;
	private JLabel lblInstalacionSabado;
	private JComboBox<Instalacion> cmbInstalacionesSabado;
	private JLabel lblHoraFinSabado;
	private JTextField txtHoraFinSabado;
	private JPanel pnModificarDomingo;
	private JLabel lblInstalacionDomingo;
	private JComboBox<Instalacion> cmbInstalacionesDomingo;
	private JLabel lblHoraFinDomingo;
	private JTextField txtHoraFinDomingo;
	private JPanel pnDecisionesHoraFin;
	private JPanel pnMismaHoraFin;
	private JRadioButton rdbtnMismaHoraFin;
	private JPanel pnDistintaHora;
	private JRadioButton rdbtnDistintaHora;
	private JTextField txtHoraFin;

	private ButtonGroup instalacionesGroup = new ButtonGroup();
	private ButtonGroup horasGroup = new ButtonGroup();

	/**
	 * Create the dialog.
	 */
	public AsignarActividadVariosDiasDialog(CalendarioSemanalPlanificar parent, Programa p, int dia, int mes, int año,
			int horaInicio) {
		setTitle("Centro de deportes: Planificando actividades el " + dia + "/" + mes + "/" + año + " a las "
				+ horaInicio);
		this.parent = parent;
		this.programa = p;
		this.dia = dia;
		this.mes = mes + 1;
		this.año = año;
		this.horaInicio = horaInicio;
		setBounds(100, 100, 699, 671);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		contentPanel.add(getPnProgramarActividad());
		contentPanel.add(getPnDias_1());
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
				modeloInstalacionesLunes.addElement(instalacion);
				modeloInstalacionesMartes.addElement(instalacion);
				modeloInstalacionesMiercoles.addElement(instalacion);
				modeloInstalacionesJueves.addElement(instalacion);
				modeloInstalacionesViernes.addElement(instalacion);
				modeloInstalacionesSabado.addElement(instalacion);
				modeloInstalacionesDomingo.addElement(instalacion);
			}
		}
	}

	public CalendarioSemanalPlanificar getParent() {
		return parent;
	}

	private Programa getPrograma() {
		return this.programa;
	}

	private JPanel getPnProgramarActividad() {
		if (pnProgramarActividad == null) {
			pnProgramarActividad = new JPanel();
			pnProgramarActividad.setLayout(new GridLayout(0, 1, 0, 0));
			pnProgramarActividad.add(getPnInfo());
			pnProgramarActividad.add(getPnInstalacion());
			pnProgramarActividad.add(getPnHoraFin());
		}
		return pnProgramarActividad;
	}

	private JButton getBtnAñadir() {
		if (btnAñadir == null) {
			btnAñadir = new JButton("Planificar");
			btnAñadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						try {
							checkCampos();
							crearActividades();
							JOptionPane.showMessageDialog(getMe(), "Se han añadido las actividades correctamente");
							getParent().generarPaneles();
							dispose();
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(getMe(),
									"Los campos de hora de fin contienen datos erróneos");
						} catch (IllegalArgumentException ex) {
							JOptionPane.showMessageDialog(getMe(), ex.getMessage());
						} catch (DateTimeParseException ex) {
							JOptionPane.showMessageDialog(getMe(),
									"Revise la fecha de finalización (formato dia/mes/año)");
						} catch (ArrayIndexOutOfBoundsException ex) {
							JOptionPane.showMessageDialog(getMe(),
									"Revise la fecha de finalización (formato dia/mes/año)");
						} catch (DateTimeException ex) {
							JOptionPane.showMessageDialog(getMe(),
									"Revise la fecha de finalización (formato dia/mes/año)");
						}

					} catch (SQLException e) {
						System.out.println(
								"Ha habido un error creando las nuevas actividades, refresca el calendario para ver los cambios");
					}
				}
			});
			btnAñadir.setForeground(Color.WHITE);
			btnAñadir.setBackground(new Color(60, 179, 113));
		}
		return btnAñadir;
	}

	protected void checkCampos() throws IllegalArgumentException, NumberFormatException, DateTimeParseException {
		int horaFin = Integer.MAX_VALUE;
		if (txtHoraFin.isVisible()) {
			horaFin = Integer.parseInt(txtHoraFin.getText());
			if (horaFin <= horaInicio) {
				throw new IllegalArgumentException("La hora de fin es anterior a la de inicio");
			}
		} else {
			if (chckbxLunes.isSelected()) {
				horaFin = Integer.parseInt(txtHoraFinLunes.getText());
				if (horaFin <= horaInicio && horaFin >= 10 && horaFin <= 23) {
					throw new IllegalArgumentException(
							"La hora de fin para el lunes es anterior a la de inicio o es inferior a las 10 o superior a las 23");
				}
			}
			if (chckbxMartes.isSelected()) {
				horaFin = Integer.parseInt(txtHoraFinMartes.getText());
				if (horaFin <= horaInicio && horaFin >= 10 && horaFin <= 23) {
					throw new IllegalArgumentException(
							"La hora de fin para el martes es anterior a la de inicio o es inferior a las 10 o superior a las 23");
				}
			}
			if (chckbxMiercoles.isSelected()) {
				horaFin = Integer.parseInt(txtHoraFinMiercoles.getText());
				if (horaFin <= horaInicio && horaFin >= 10 && horaFin <= 23) {
					throw new IllegalArgumentException(
							"La hora de fin para el miercoles es anterior a la de inicio o es inferior a las 10 o superior a las 23");
				}
			}
			if (chckbxJueves.isSelected()) {
				horaFin = Integer.parseInt(txtHoraFinJueves.getText());
				if (horaFin <= horaInicio && horaFin >= 10 && horaFin <= 23) {
					throw new IllegalArgumentException(
							"La hora de fin para el jueves es anterior a la de inicio o es inferior a las 10 o superior a las 23");
				}
			}
			if (chckbxViernes.isSelected()) {
				horaFin = Integer.parseInt(txtHoraFinViernes.getText());
				if (horaFin <= horaInicio && horaFin >= 10 && horaFin <= 23) {
					throw new IllegalArgumentException(
							"La hora de fin para el viernes es anterior a la de inicio o es inferior a las 10 o superior a las 23");
				}
			}
			if (chckbxSabado.isSelected()) {
				horaFin = Integer.parseInt(txtHoraFinSabado.getText());
				if (horaFin <= horaInicio && horaFin >= 10 && horaFin <= 23) {
					throw new IllegalArgumentException(
							"La hora de fin para el sabado es anterior a la de inicio o es inferior a las 10 o superior a las 23");
				}
			}
			if (chckbxDomingo.isSelected()) {
				horaFin = Integer.parseInt(txtHoraFinDomingo.getText());
				if (horaFin <= horaInicio && horaFin >= 10 && horaFin <= 23) {
					throw new IllegalArgumentException(
							"La hora de fin para el domingo es anterior a la de inicio o es inferior a las 10 o superior a las 23");
				}
			}
		}
		int diaFin = -1;
		int mesFin = -1;
		int añoFin = -1;
		if (!txtFechaFin.getText().equals("")) {
			diaFin = Integer.parseInt(txtFechaFin.getText().split("/")[0]);
			mesFin = Integer.parseInt(txtFechaFin.getText().split("/")[1]);
			añoFin = Integer.parseInt(txtFechaFin.getText().split("/")[2]);
		}
		if (LocalDate.of(añoFin, mesFin, diaFin).isBefore(LocalDate.of(año, mes, dia))) {
			throw new IllegalArgumentException(
					"La fecha de finalización de la actividad es anterior a la fecha seleccionada");
		}
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

	private void crearActividades() throws SQLException {
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
		int horaFin = 0;
		String codigoInstalacion = "";
		while (LocalDate.of(añoUltimaReserva, mesUltimaReserva, diaUltimaReserva)
				.isBefore(LocalDate.of(añoFin, mesFin, diaFin))) {

			LocalDate diaIterable = LocalDate.of(añoUltimaReserva, mesUltimaReserva, diaUltimaReserva);
			String codigoActividad = ((Actividad) cmbActividades.getSelectedItem()).getCodigo();

			if (rdbtnUnaInstalacion.isSelected()) {
				codigoInstalacion = ((Instalacion) cmbInstalaciones.getSelectedItem()).getCodigoInstalacion();
			}

			horaFin = Integer.parseInt(txtHoraFin.getText());
			ActividadPlanificada actividadACrear;

			if (diaIterable.getDayOfWeek().getValue() == 1) {
				if (chckbxLunes.isSelected()) {
					if (rdbtnVariasInstalaciones.isSelected()) {
						codigoInstalacion = ((Instalacion) cmbInstalacionesLunes.getSelectedItem())
								.getCodigoInstalacion();
					}
					if (rdbtnDistintaHora.isSelected()) {
						horaFin = Integer.parseInt(txtHoraFinLunes.getText());
					}
					actividadACrear = new ActividadPlanificada(codigoActividad, codigoInstalacion, horaInicio, horaFin,
							diaUltimaReserva, mesUltimaReserva, añoUltimaReserva);
					checkConflictos(actividadACrear);
					getPrograma().añadirActividadPlanificada(actividadACrear);
					
				}
			}
			if (diaIterable.getDayOfWeek().getValue() == 2) {
				if (chckbxMartes.isSelected()) {
					if (rdbtnVariasInstalaciones.isSelected()) {
						codigoInstalacion = ((Instalacion) cmbInstalacionesMartes.getSelectedItem())
								.getCodigoInstalacion();
					}
					if (rdbtnDistintaHora.isSelected()) {
						horaFin = Integer.parseInt(txtHoraFinMartes.getText());
					}
					actividadACrear = new ActividadPlanificada(codigoActividad, codigoInstalacion, horaInicio, horaFin,
							diaUltimaReserva, mesUltimaReserva, añoUltimaReserva);
					checkConflictos(actividadACrear);
					getPrograma().añadirActividadPlanificada(actividadACrear);
					
				}
			}
			if (diaIterable.getDayOfWeek().getValue() == 3) {
				if (chckbxMiercoles.isSelected()) {
					if (rdbtnVariasInstalaciones.isSelected()) {
						codigoInstalacion = ((Instalacion) cmbInstalacionesMiercoles.getSelectedItem())
								.getCodigoInstalacion();
					}
					if (rdbtnDistintaHora.isSelected()) {
						horaFin = Integer.parseInt(txtHoraFinMiercoles.getText());
					}
					actividadACrear = new ActividadPlanificada(codigoActividad, codigoInstalacion, horaInicio, horaFin,
							diaUltimaReserva, mesUltimaReserva, añoUltimaReserva);
					checkConflictos(actividadACrear);
					getPrograma().añadirActividadPlanificada(actividadACrear);
					
				}
			}
			if (diaIterable.getDayOfWeek().getValue() == 4) {
				if (chckbxJueves.isSelected()) {
					if (rdbtnVariasInstalaciones.isSelected()) {
						codigoInstalacion = ((Instalacion) cmbInstalacionesJueves.getSelectedItem())
								.getCodigoInstalacion();
					}
					if (rdbtnDistintaHora.isSelected()) {
						horaFin = Integer.parseInt(txtHoraFinJueves.getText());
					}
					actividadACrear = new ActividadPlanificada(codigoActividad, codigoInstalacion, horaInicio, horaFin,
							diaUltimaReserva, mesUltimaReserva, añoUltimaReserva);
					checkConflictos(actividadACrear);
					getPrograma().añadirActividadPlanificada(actividadACrear);
					
				}
			}
			if (diaIterable.getDayOfWeek().getValue() == 5) {
				if (chckbxViernes.isSelected()) {
					if (rdbtnVariasInstalaciones.isSelected()) {
						codigoInstalacion = ((Instalacion) cmbInstalacionesViernes.getSelectedItem())
								.getCodigoInstalacion();
					}
					if (rdbtnDistintaHora.isSelected()) {
						horaFin = Integer.parseInt(txtHoraFinViernes.getText());
					}
					actividadACrear = new ActividadPlanificada(codigoActividad, codigoInstalacion, horaInicio, horaFin,
							diaUltimaReserva, mesUltimaReserva, añoUltimaReserva);
					checkConflictos(actividadACrear);
					getPrograma().añadirActividadPlanificada(actividadACrear);
					
				}
			}
			if (diaIterable.getDayOfWeek().getValue() == 6) {
				if (chckbxSabado.isSelected()) {
					if (rdbtnVariasInstalaciones.isSelected()) {
						codigoInstalacion = ((Instalacion) cmbInstalacionesSabado.getSelectedItem())
								.getCodigoInstalacion();
					}
					if (rdbtnDistintaHora.isSelected()) {
						horaFin = Integer.parseInt(txtHoraFinSabado.getText());
					}
					actividadACrear = new ActividadPlanificada(codigoActividad, codigoInstalacion, horaInicio, horaFin,
							diaUltimaReserva, mesUltimaReserva, añoUltimaReserva);
					checkConflictos(actividadACrear);
					getPrograma().añadirActividadPlanificada(actividadACrear);
					
				}
			}
			if (diaIterable.getDayOfWeek().getValue() == 7) {
				if (chckbxDomingo.isSelected()) {
					if (rdbtnVariasInstalaciones.isSelected()) {
						codigoInstalacion = ((Instalacion) cmbInstalacionesDomingo.getSelectedItem())
								.getCodigoInstalacion();
					}
					if (rdbtnDistintaHora.isSelected()) {
						horaFin = Integer.parseInt(txtHoraFinDomingo.getText());
					}
					actividadACrear = new ActividadPlanificada(codigoActividad, codigoInstalacion, horaInicio, horaFin,
							diaUltimaReserva, mesUltimaReserva, añoUltimaReserva);
					checkConflictos(actividadACrear);
					getPrograma().añadirActividadPlanificada(actividadACrear);
					
				}
			}
			
			
			
			diaUltimaReserva += 1;

			if (mesUltimaReserva == 1 || mesUltimaReserva == 3 || mesUltimaReserva == 5 || mesUltimaReserva == 7
					|| mesUltimaReserva == 8 || mesUltimaReserva == 9 || mesUltimaReserva == 10
					|| mesUltimaReserva == 12) {
				if (diaUltimaReserva > 31) {
					mesUltimaReserva++;
					diaUltimaReserva = 1;
					if (mesUltimaReserva > 12) {
						mesUltimaReserva -= 12;
						añoUltimaReserva++;
					}
				}
			} else {
				if (diaUltimaReserva > 30) {
					mesUltimaReserva++;
					diaUltimaReserva = 1;
					if (mesUltimaReserva > 12) {
						mesUltimaReserva -= 12;
						añoUltimaReserva++;
					}
				}
			}
		}
	}

	private JPanel getPnInfo() {
		if (pnInfo == null) {
			pnInfo = new JPanel();
			pnInfo.setLayout(new GridLayout(1, 2, 0, 0));
			pnInfo.add(getPnActividad());
			pnInfo.add(getPnFechaFin());
		}
		return pnInfo;
	}

	private JPanel getPnInstalacion() {
		if (pnInstalacion == null) {
			pnInstalacion = new JPanel();
			pnInstalacion.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
					"Instalaci\u00F3n donde se realizar\u00E1n las actividades:", TitledBorder.LEADING,
					TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnInstalacion.setLayout(new GridLayout(0, 1, 0, 0));
			pnInstalacion.add(getPnDecisionesInstalaciones());
		}
		return pnInstalacion;
	}

	private JPanel getPnHoraFin() {
		if (pnHoraFin == null) {
			pnHoraFin = new JPanel();
			pnHoraFin.setBorder(new TitledBorder(null, "Selecci\u00F3n de hora de finalizaci\u00F3n de la actividad:",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnHoraFin.setLayout(new GridLayout(1, 2, 0, 0));
			pnHoraFin.add(getPnDecisionesHoraFin());
		}
		return pnHoraFin;
	}

	private JPanel getPnDecisionesInstalaciones() {
		if (pnDecisionesInstalaciones == null) {
			pnDecisionesInstalaciones = new JPanel();
			pnDecisionesInstalaciones.setLayout(new GridLayout(0, 2, 0, 0));
			pnDecisionesInstalaciones.add(getPnUnaSolaInstalación());
			pnDecisionesInstalaciones.add(getPnVariasInstalaciones());
		}
		return pnDecisionesInstalaciones;
	}

	private JPanel getPnUnaSolaInstalación() {
		if (pnUnaSolaInstalación == null) {
			pnUnaSolaInstalación = new JPanel();
			pnUnaSolaInstalación.setLayout(new GridLayout(0, 1, 0, 0));
			pnUnaSolaInstalación.add(getRdbtnUnaInstalacion());
			pnUnaSolaInstalación.add(getCmbInstalaciones());

		}
		return pnUnaSolaInstalación;
	}

	private JPanel getPnVariasInstalaciones() {
		if (pnVariasInstalaciones == null) {
			pnVariasInstalaciones = new JPanel();
			pnVariasInstalaciones.add(getRdbtnVariasInstalaciones());
		}
		return pnVariasInstalaciones;
	}

	private JRadioButton getRdbtnUnaInstalacion() {
		if (rdbtnUnaInstalacion == null) {
			rdbtnUnaInstalacion = new JRadioButton("Misma instalaci\u00F3n para todas las planificadas");
			rdbtnUnaInstalacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cmbInstalaciones.setVisible(true);
					cmbInstalacionesLunes.setVisible(false);
					cmbInstalacionesMartes.setVisible(false);
					cmbInstalacionesMiercoles.setVisible(false);
					cmbInstalacionesJueves.setVisible(false);
					cmbInstalacionesViernes.setVisible(false);
					cmbInstalacionesSabado.setVisible(false);
					cmbInstalacionesDomingo.setVisible(false);

					lblInstalacionLunes.setVisible(false);
					lblInstalacionMartes.setVisible(false);
					lblInstalacionMiercoles.setVisible(false);
					lblInstalacionJueves.setVisible(false);
					lblInstalacionViernes.setVisible(false);
					lblInstalacionSabado.setVisible(false);
					lblInstalacionDomingo.setVisible(false);
				}
			});
			rdbtnUnaInstalacion.setHorizontalAlignment(SwingConstants.CENTER);
			rdbtnUnaInstalacion.setSelected(true);
			instalacionesGroup.add(rdbtnUnaInstalacion);
		}
		return rdbtnUnaInstalacion;
	}

	private JRadioButton getRdbtnVariasInstalaciones() {
		if (rdbtnVariasInstalaciones == null) {
			rdbtnVariasInstalaciones = new JRadioButton("Decidir la instalaci\u00F3n para cada d\u00EDa de la semana");
			rdbtnVariasInstalaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cmbInstalaciones.setVisible(false);
					if (chckbxLunes.isSelected()) {
						lblInstalacionLunes.setVisible(true);
						cmbInstalacionesLunes.setVisible(true);
					}
					if (chckbxMartes.isSelected()) {
						cmbInstalacionesMartes.setVisible(true);
						lblInstalacionMartes.setVisible(true);
					}
					if (chckbxMiercoles.isSelected()) {
						cmbInstalacionesMiercoles.setVisible(true);
						lblInstalacionMiercoles.setVisible(true);
					}
					if (chckbxJueves.isSelected()) {
						cmbInstalacionesJueves.setVisible(true);
						lblInstalacionJueves.setVisible(true);
					}
					if (chckbxViernes.isSelected()) {
						cmbInstalacionesViernes.setVisible(true);
						lblInstalacionViernes.setVisible(true);
					}
					if (chckbxSabado.isSelected()) {
						cmbInstalacionesSabado.setVisible(true);
						lblInstalacionSabado.setVisible(true);
					}
					if (chckbxDomingo.isSelected()) {
						cmbInstalacionesDomingo.setVisible(true);
						lblInstalacionDomingo.setVisible(true);
					}
				}
			});
			instalacionesGroup.add(rdbtnVariasInstalaciones);
		}
		return rdbtnVariasInstalaciones;
	}

	private JComboBox<Instalacion> getCmbInstalaciones() {
		if (cmbInstalaciones == null) {
			cmbInstalaciones = new JComboBox<Instalacion>(modeloInstalaciones);
			cmbInstalaciones.setEnabled(true);
		}
		return cmbInstalaciones;
	}

	private JPanel getPnDias_1() {
		if (pnDias == null) {
			pnDias = new JPanel();
			pnDias.setBorder(new TitledBorder(null, "Repetir los d\u00EDas:", TitledBorder.LEADING, TitledBorder.TOP,
					null, null));
			pnDias.setLayout(new BorderLayout(0, 0));
			pnDias.add(getPnSemana_1());
		}
		return pnDias;
	}

	private JPanel getPnSemana_1() {
		if (pnSemana == null) {
			pnSemana = new JPanel();
			pnSemana.setLayout(new GridLayout(0, 2, 0, 0));
			pnSemana.add(getPnPrimera_1());
			pnSemana.add(getPnSegunda_1());
		}
		return pnSemana;
	}

	private JPanel getPnPrimera_1() {
		if (pnPrimera == null) {
			pnPrimera = new JPanel();
			pnPrimera.setLayout(new GridLayout(0, 1, 0, 0));
			pnPrimera.add(getPnLunes());
			pnPrimera.add(getPnMartes());
			pnPrimera.add(getPnMiercoles());
			pnPrimera.add(getPnJueves());
		}
		return pnPrimera;
	}

	private JPanel getPnLunes() {
		if (pnLunes == null) {
			pnLunes = new JPanel();
			pnLunes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			pnLunes.setLayout(new BorderLayout(0, 0));
			pnLunes.add(getChckbxLunes(), BorderLayout.NORTH);
			pnLunes.add(getPnModificarLunes(), BorderLayout.CENTER);
		}
		return pnLunes;
	}

	private JCheckBox getChckbxLunes() {
		if (chckbxLunes == null) {
			chckbxLunes = new JCheckBox("Lunes");
			chckbxLunes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (chckbxLunes.isSelected()) {
						if (rdbtnVariasInstalaciones.isSelected()) {
							cmbInstalacionesLunes.setVisible(true);
							lblInstalacionLunes.setVisible(true);
						} else {
							cmbInstalacionesLunes.setVisible(false);
							lblInstalacionLunes.setVisible(false);
						}
						if (rdbtnDistintaHora.isSelected()) {
							txtHoraFinLunes.setVisible(true);
							lblHoraFinLunes.setVisible(true);
						} else {
							txtHoraFinLunes.setVisible(false);
							lblHoraFinLunes.setVisible(false);
						}
					} else {
						cmbInstalacionesLunes.setVisible(false);
						lblInstalacionLunes.setVisible(false);
						txtHoraFinLunes.setVisible(false);
						lblHoraFinLunes.setVisible(false);
					}
				}
			});
			chckbxLunes.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return chckbxLunes;
	}

	private JPanel getPnMartes() {
		if (pnMartes == null) {
			pnMartes = new JPanel();
			pnMartes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			pnMartes.setLayout(new BorderLayout(0, 0));
			pnMartes.add(getChckbxMartes(), BorderLayout.NORTH);
			pnMartes.add(getPnModificarMartes());
		}
		return pnMartes;
	}

	private JCheckBox getChckbxMartes() {
		if (chckbxMartes == null) {
			chckbxMartes = new JCheckBox("Martes");
			chckbxMartes.setHorizontalAlignment(SwingConstants.CENTER);
			chckbxMartes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (chckbxMartes.isSelected()) {
						if (rdbtnVariasInstalaciones.isSelected()) {
							cmbInstalacionesMartes.setVisible(true);
							lblInstalacionMartes.setVisible(true);
						} else {
							cmbInstalacionesMartes.setVisible(false);
							lblInstalacionMartes.setVisible(false);
						}
						if (rdbtnDistintaHora.isSelected()) {
							txtHoraFinMartes.setVisible(true);
							lblHoraFinMartes.setVisible(true);
						} else {
							txtHoraFinMartes.setVisible(false);
							lblHoraFinMartes.setVisible(false);
						}
					} else {
						cmbInstalacionesMartes.setVisible(false);
						lblInstalacionMartes.setVisible(false);
						txtHoraFinMartes.setVisible(false);
						lblHoraFinMartes.setVisible(false);
					}
				}
			});
		}
		return chckbxMartes;
	}

	private JPanel getPnMiercoles() {
		if (pnMiercoles == null) {
			pnMiercoles = new JPanel();
			pnMiercoles.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			pnMiercoles.setLayout(new BorderLayout(0, 0));
			pnMiercoles.add(getChckbxMiercoles(), BorderLayout.NORTH);
			pnMiercoles.add(getPnModificarMiercoles());
		}
		return pnMiercoles;
	}

	private JCheckBox getChckbxMiercoles() {
		if (chckbxMiercoles == null) {
			chckbxMiercoles = new JCheckBox("Mi\u00E9rcoles");
			chckbxMiercoles.setHorizontalAlignment(SwingConstants.CENTER);
			chckbxMiercoles.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (chckbxMiercoles.isSelected()) {
						if (rdbtnVariasInstalaciones.isSelected()) {
							cmbInstalacionesMiercoles.setVisible(true);
							lblInstalacionMiercoles.setVisible(true);
						} else {
							cmbInstalacionesMiercoles.setVisible(false);
							lblInstalacionMiercoles.setVisible(false);
						}
						if (rdbtnDistintaHora.isSelected()) {
							txtHoraFinMiercoles.setVisible(true);
							lblHoraFinMiercoles.setVisible(true);
						} else {
							txtHoraFinMiercoles.setVisible(false);
							lblHoraFinMiercoles.setVisible(false);
						}
					} else {
						cmbInstalacionesMiercoles.setVisible(false);
						lblInstalacionMiercoles.setVisible(false);
					}
				}
			});
		}
		return chckbxMiercoles;
	}

	private JPanel getPnJueves() {
		if (pnJueves == null) {
			pnJueves = new JPanel();
			pnJueves.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			pnJueves.setLayout(new BorderLayout(0, 0));
			pnJueves.add(getChckbxJueves(), BorderLayout.NORTH);
			pnJueves.add(getPnModificarJueves());
		}
		return pnJueves;
	}

	private JCheckBox getChckbxJueves() {
		if (chckbxJueves == null) {
			chckbxJueves = new JCheckBox("Jueves");
			chckbxJueves.setHorizontalAlignment(SwingConstants.CENTER);
			chckbxJueves.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (chckbxJueves.isSelected()) {
						if (rdbtnVariasInstalaciones.isSelected()) {
							cmbInstalacionesJueves.setVisible(true);
							lblInstalacionJueves.setVisible(true);
						} else {
							cmbInstalacionesJueves.setVisible(false);
							lblInstalacionJueves.setVisible(false);
						}
						if (rdbtnDistintaHora.isSelected()) {
							txtHoraFinJueves.setVisible(true);
							lblHoraFinJueves.setVisible(true);
						} else {
							txtHoraFinJueves.setVisible(false);
							lblHoraFinJueves.setVisible(false);
						}
					} else {
						cmbInstalacionesJueves.setVisible(false);
						lblInstalacionJueves.setVisible(false);
						txtHoraFinJueves.setVisible(false);
						lblHoraFinJueves.setVisible(false);
					}
				}
			});
		}
		return chckbxJueves;
	}

	private JPanel getPnSegunda_1() {
		if (pnSegunda == null) {
			pnSegunda = new JPanel();
			pnSegunda.setLayout(new GridLayout(0, 1, 0, 0));
			pnSegunda.add(getPnViernes());
			pnSegunda.add(getPnSabado());
			pnSegunda.add(getPnDomingo());
		}
		return pnSegunda;
	}

	private JPanel getPnViernes() {
		if (pnViernes == null) {
			pnViernes = new JPanel();
			pnViernes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			pnViernes.setLayout(new BorderLayout(0, 0));
			pnViernes.add(getChckbxViernes(), BorderLayout.NORTH);
			pnViernes.add(getPnModificarViernes());
		}
		return pnViernes;
	}

	private JCheckBox getChckbxViernes() {
		if (chckbxViernes == null) {
			chckbxViernes = new JCheckBox("Viernes");
			chckbxViernes.setHorizontalAlignment(SwingConstants.CENTER);
			chckbxViernes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (chckbxViernes.isSelected()) {
						if (rdbtnVariasInstalaciones.isSelected()) {
							cmbInstalacionesViernes.setVisible(true);
							lblInstalacionViernes.setVisible(true);
						} else {
							cmbInstalacionesViernes.setVisible(false);
							lblInstalacionViernes.setVisible(false);
						}
						if (rdbtnDistintaHora.isSelected()) {
							txtHoraFinViernes.setVisible(true);
							lblHoraFinViernes.setVisible(true);
						} else {
							txtHoraFinViernes.setVisible(false);
							lblHoraFinViernes.setVisible(false);
						}
					} else {
						cmbInstalacionesViernes.setVisible(false);
						lblInstalacionViernes.setVisible(false);
						txtHoraFinViernes.setVisible(false);
						lblHoraFinViernes.setVisible(false);
					}
				}
			});
		}
		return chckbxViernes;
	}

	private JPanel getPnSabado() {
		if (pnSabado == null) {
			pnSabado = new JPanel();
			pnSabado.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			pnSabado.setLayout(new BorderLayout(0, 0));
			pnSabado.add(getChckbxSabado(), BorderLayout.NORTH);
			pnSabado.add(getPnModificarSabado());
		}
		return pnSabado;
	}

	private JCheckBox getChckbxSabado() {
		if (chckbxSabado == null) {
			chckbxSabado = new JCheckBox("S\u00E1bado");
			chckbxSabado.setHorizontalAlignment(SwingConstants.CENTER);
			chckbxSabado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (chckbxSabado.isSelected()) {
						if (rdbtnVariasInstalaciones.isSelected()) {
							cmbInstalacionesSabado.setVisible(true);
							lblInstalacionSabado.setVisible(true);
						} else {
							cmbInstalacionesSabado.setVisible(false);
							lblInstalacionSabado.setVisible(false);
						}
						if (rdbtnDistintaHora.isSelected()) {
							txtHoraFinSabado.setVisible(true);
							lblHoraFinSabado.setVisible(true);
						} else {
							txtHoraFinSabado.setVisible(false);
							lblHoraFinSabado.setVisible(false);
						}
					} else {
						cmbInstalacionesSabado.setVisible(false);
						lblInstalacionSabado.setVisible(false);
						txtHoraFinSabado.setVisible(false);
						lblHoraFinSabado.setVisible(false);
					}
				}
			});
		}
		return chckbxSabado;
	}

	private JPanel getPnDomingo() {
		if (pnDomingo == null) {
			pnDomingo = new JPanel();
			pnDomingo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			pnDomingo.setLayout(new BorderLayout(0, 0));
			pnDomingo.add(getChckbxDomingo(), BorderLayout.NORTH);
			pnDomingo.add(getPnModificarDomingo());
		}
		return pnDomingo;
	}

	private JCheckBox getChckbxDomingo() {
		if (chckbxDomingo == null) {
			chckbxDomingo = new JCheckBox("Domingo");
			chckbxDomingo.setHorizontalAlignment(SwingConstants.CENTER);
			chckbxDomingo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (chckbxDomingo.isSelected()) {
						if (rdbtnVariasInstalaciones.isSelected()) {
							cmbInstalacionesDomingo.setVisible(true);
							lblInstalacionDomingo.setVisible(true);
						} else {
							cmbInstalacionesDomingo.setVisible(false);
							lblInstalacionDomingo.setVisible(false);
						}
						if (rdbtnDistintaHora.isSelected()) {
							txtHoraFinDomingo.setVisible(true);
							lblHoraFinDomingo.setVisible(true);
						} else {
							txtHoraFinDomingo.setVisible(false);
							lblHoraFinDomingo.setVisible(false);
						}
					} else {
						cmbInstalacionesDomingo.setVisible(false);
						lblInstalacionDomingo.setVisible(false);
						txtHoraFinDomingo.setVisible(false);
						lblHoraFinDomingo.setVisible(false);
					}
				}
			});
		}
		return chckbxDomingo;
	}

	private JPanel getPnModificarLunes() {
		if (pnModificarLunes == null) {
			pnModificarLunes = new JPanel();
			pnModificarLunes.setLayout(new GridLayout(0, 2, 0, 0));
			pnModificarLunes.add(getLblInstalacionLunes());
			pnModificarLunes.add(getCmbInstalacionesLunes());
			pnModificarLunes.add(getLblHoraFinLunes());
			pnModificarLunes.add(getTxtHoraFinLunes());
		}
		return pnModificarLunes;
	}

	private JLabel getLblInstalacionLunes() {
		if (lblInstalacionLunes == null) {
			lblInstalacionLunes = new JLabel("Instalaci\u00F3n:");
			lblInstalacionLunes.setHorizontalAlignment(SwingConstants.CENTER);
			lblInstalacionLunes.setVisible(false);
		}
		return lblInstalacionLunes;
	}

	private JComboBox<Instalacion> getCmbInstalacionesLunes() {
		if (cmbInstalacionesLunes == null) {
			cmbInstalacionesLunes = new JComboBox<Instalacion>(modeloInstalacionesLunes);
			cmbInstalacionesLunes.setEnabled(true);
			cmbInstalacionesLunes.setVisible(false);
		}
		return cmbInstalacionesLunes;
	}

	private JLabel getLblHoraFinLunes() {
		if (lblHoraFinLunes == null) {
			lblHoraFinLunes = new JLabel("Hora fin:");
			lblHoraFinLunes.setHorizontalAlignment(SwingConstants.CENTER);
			lblHoraFinLunes.setVisible(false);
		}
		return lblHoraFinLunes;
	}

	private JTextField getTxtHoraFinLunes() {
		if (txtHoraFinLunes == null) {
			txtHoraFinLunes = new JTextField();
			txtHoraFinLunes.setColumns(10);
			txtHoraFinLunes.setVisible(false);
		}
		return txtHoraFinLunes;
	}

	private JPanel getPnActividad() {
		if (pnActividad == null) {
			pnActividad = new JPanel();
			pnActividad.setBorder(new TitledBorder(null, "Actividad a planificar:", TitledBorder.LEADING,
					TitledBorder.TOP, null, null));
			pnActividad.setLayout(new BorderLayout(0, 0));
			pnActividad.add(getCmbActividades_1());
		}
		return pnActividad;
	}

	private JComboBox<Actividad> getCmbActividades_1() {
		if (cmbActividades == null) {
			cmbActividades = new JComboBox<Actividad>(modeloActividades);
		}
		return cmbActividades;
	}

	private JPanel getPnFechaFin() {
		if (pnFechaFin == null) {
			pnFechaFin = new JPanel();
			pnFechaFin.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
					"Fecha en la que concluyen las actividades ", TitledBorder.LEADING, TitledBorder.TOP, null,
					new Color(0, 0, 0)));
			pnFechaFin.setLayout(new BorderLayout(0, 0));
			pnFechaFin.add(getTxtFechaFin());
		}
		return pnFechaFin;
	}

	private JTextField getTxtFechaFin() {
		if (txtFechaFin == null) {
			txtFechaFin = new JTextField();
			txtFechaFin.setToolTipText("Formato (d\u00EDa/mes/a\u00F1o)");
			txtFechaFin.setColumns(10);
		}
		return txtFechaFin;
	}

	private JPanel getPnModificarMartes() {
		if (pnModificarMartes == null) {
			pnModificarMartes = new JPanel();
			pnModificarMartes.setLayout(new GridLayout(0, 2, 0, 0));
			pnModificarMartes.add(getLblInstalacionMartes());
			pnModificarMartes.add(getCmbInstalacionesMartes());
			pnModificarMartes.add(getLblHoraFinMartes());
			pnModificarMartes.add(getTxtHoraFinMartes());
		}
		return pnModificarMartes;
	}

	private JLabel getLblInstalacionMartes() {
		if (lblInstalacionMartes == null) {
			lblInstalacionMartes = new JLabel("Instalaci\u00F3n:");
			lblInstalacionMartes.setHorizontalAlignment(SwingConstants.CENTER);
			lblInstalacionMartes.setVisible(false);
		}
		return lblInstalacionMartes;
	}

	private JComboBox<Instalacion> getCmbInstalacionesMartes() {
		if (cmbInstalacionesMartes == null) {
			cmbInstalacionesMartes = new JComboBox<Instalacion>(modeloInstalacionesMartes);
			cmbInstalacionesMartes.setEnabled(true);
			cmbInstalacionesMartes.setVisible(false);
		}
		return cmbInstalacionesMartes;
	}

	private JLabel getLblHoraFinMartes() {
		if (lblHoraFinMartes == null) {
			lblHoraFinMartes = new JLabel("Hora fin:");
			lblHoraFinMartes.setHorizontalAlignment(SwingConstants.CENTER);
			lblHoraFinMartes.setVisible(false);
		}
		return lblHoraFinMartes;
	}

	private JTextField getTxtHoraFinMartes() {
		if (txtHoraFinMartes == null) {
			txtHoraFinMartes = new JTextField();
			txtHoraFinMartes.setColumns(10);
			txtHoraFinMartes.setVisible(false);
		}
		return txtHoraFinMartes;
	}

	private JPanel getPnModificarMiercoles() {
		if (pnModificarMiercoles == null) {
			pnModificarMiercoles = new JPanel();
			pnModificarMiercoles.setLayout(new GridLayout(0, 2, 0, 0));
			pnModificarMiercoles.add(getLblInstalacionMiercoles());
			pnModificarMiercoles.add(getCmbInstalacionesMiercoles());
			pnModificarMiercoles.add(getLblHoraFinMiercoles());
			pnModificarMiercoles.add(getTxtHoraFinMiercoles());
		}
		return pnModificarMiercoles;
	}

	private JLabel getLblInstalacionMiercoles() {
		if (lblInstalacionMiercoles == null) {
			lblInstalacionMiercoles = new JLabel("Instalaci\u00F3n:");
			lblInstalacionMiercoles.setHorizontalAlignment(SwingConstants.CENTER);
			lblInstalacionMiercoles.setVisible(false);
		}
		return lblInstalacionMiercoles;
	}

	private JComboBox<Instalacion> getCmbInstalacionesMiercoles() {
		if (cmbInstalacionesMiercoles == null) {
			cmbInstalacionesMiercoles = new JComboBox<Instalacion>(modeloInstalacionesMiercoles);
			cmbInstalacionesMiercoles.setEnabled(true);
			cmbInstalacionesMiercoles.setVisible(false);
		}
		return cmbInstalacionesMiercoles;
	}

	private JLabel getLblHoraFinMiercoles() {
		if (lblHoraFinMiercoles == null) {
			lblHoraFinMiercoles = new JLabel("Hora fin:");
			lblHoraFinMiercoles.setHorizontalAlignment(SwingConstants.CENTER);
			lblHoraFinMiercoles.setVisible(false);
		}
		return lblHoraFinMiercoles;
	}

	private JTextField getTxtHoraFinMiercoles() {
		if (txtHoraFinMiercoles == null) {
			txtHoraFinMiercoles = new JTextField();
			txtHoraFinMiercoles.setColumns(10);
			txtHoraFinMiercoles.setVisible(false);
		}
		return txtHoraFinMiercoles;
	}

	private JPanel getPnModificarJueves() {
		if (pnModificarJueves == null) {
			pnModificarJueves = new JPanel();
			pnModificarJueves.setLayout(new GridLayout(0, 2, 0, 0));
			pnModificarJueves.add(getLblInstalacionJueves());
			pnModificarJueves.add(getCmbInstalacionesJueves());
			pnModificarJueves.add(getLblHoraFinJueves());
			pnModificarJueves.add(getTxtHoraFinJueves());
		}
		return pnModificarJueves;
	}

	private JLabel getLblInstalacionJueves() {
		if (lblInstalacionJueves == null) {
			lblInstalacionJueves = new JLabel("Instalaci\u00F3n:");
			lblInstalacionJueves.setHorizontalAlignment(SwingConstants.CENTER);
			lblInstalacionJueves.setVisible(false);
		}
		return lblInstalacionJueves;
	}

	private JComboBox<Instalacion> getCmbInstalacionesJueves() {
		if (cmbInstalacionesJueves == null) {
			cmbInstalacionesJueves = new JComboBox<Instalacion>(modeloInstalacionesJueves);
			cmbInstalacionesJueves.setEnabled(true);
			cmbInstalacionesJueves.setVisible(false);
		}
		return cmbInstalacionesJueves;
	}

	private JLabel getLblHoraFinJueves() {
		if (lblHoraFinJueves == null) {
			lblHoraFinJueves = new JLabel("Hora fin:");
			lblHoraFinJueves.setHorizontalAlignment(SwingConstants.CENTER);
			lblHoraFinJueves.setVisible(false);
		}
		return lblHoraFinJueves;
	}

	private JTextField getTxtHoraFinJueves() {
		if (txtHoraFinJueves == null) {
			txtHoraFinJueves = new JTextField();
			txtHoraFinJueves.setColumns(10);
			txtHoraFinJueves.setVisible(false);
		}
		return txtHoraFinJueves;
	}

	private JPanel getPnModificarViernes() {
		if (pnModificarViernes == null) {
			pnModificarViernes = new JPanel();
			pnModificarViernes.setLayout(new GridLayout(0, 2, 0, 0));
			pnModificarViernes.add(getLblInstalacionViernes());
			pnModificarViernes.add(getCmbInstalacionesViernes());
			pnModificarViernes.add(getLblHoraFinViernes());
			pnModificarViernes.add(getTxtHoraFinViernes());
		}
		return pnModificarViernes;
	}

	private JLabel getLblInstalacionViernes() {
		if (lblInstalacionViernes == null) {
			lblInstalacionViernes = new JLabel("Instalaci\u00F3n:");
			lblInstalacionViernes.setHorizontalAlignment(SwingConstants.CENTER);
			lblInstalacionViernes.setVisible(false);
		}
		return lblInstalacionViernes;
	}

	private JComboBox<Instalacion> getCmbInstalacionesViernes() {
		if (cmbInstalacionesViernes == null) {
			cmbInstalacionesViernes = new JComboBox<Instalacion>(modeloInstalacionesViernes);
			cmbInstalacionesViernes.setEnabled(true);
			cmbInstalacionesViernes.setVisible(false);
		}
		return cmbInstalacionesViernes;
	}

	private JLabel getLblHoraFinViernes() {
		if (lblHoraFinViernes == null) {
			lblHoraFinViernes = new JLabel("Hora fin:");
			lblHoraFinViernes.setHorizontalAlignment(SwingConstants.CENTER);
			lblHoraFinViernes.setVisible(false);
		}
		return lblHoraFinViernes;
	}

	private JTextField getTxtHoraFinViernes() {
		if (txtHoraFinViernes == null) {
			txtHoraFinViernes = new JTextField();
			txtHoraFinViernes.setColumns(10);
			txtHoraFinViernes.setVisible(false);
		}
		return txtHoraFinViernes;
	}

	private JPanel getPnModificarSabado() {
		if (pnModificarSabado == null) {
			pnModificarSabado = new JPanel();
			pnModificarSabado.setLayout(new GridLayout(0, 2, 0, 0));
			pnModificarSabado.add(getLblInstalacionSabado());
			pnModificarSabado.add(getCmbInstalacionesSabado());
			pnModificarSabado.add(getLblHoraFinSabado());
			pnModificarSabado.add(getTxtHoraFinSabado());
		}
		return pnModificarSabado;
	}

	private JLabel getLblInstalacionSabado() {
		if (lblInstalacionSabado == null) {
			lblInstalacionSabado = new JLabel("Instalaci\u00F3n:");
			lblInstalacionSabado.setHorizontalAlignment(SwingConstants.CENTER);
			lblInstalacionSabado.setVisible(false);
		}
		return lblInstalacionSabado;
	}

	private JComboBox<Instalacion> getCmbInstalacionesSabado() {
		if (cmbInstalacionesSabado == null) {
			cmbInstalacionesSabado = new JComboBox<Instalacion>(modeloInstalacionesSabado);
			cmbInstalacionesSabado.setEnabled(true);
			cmbInstalacionesSabado.setVisible(false);
		}
		return cmbInstalacionesSabado;
	}

	private JLabel getLblHoraFinSabado() {
		if (lblHoraFinSabado == null) {
			lblHoraFinSabado = new JLabel("Hora fin:");
			lblHoraFinSabado.setHorizontalAlignment(SwingConstants.CENTER);
			lblHoraFinSabado.setVisible(false);
		}
		return lblHoraFinSabado;
	}

	private JTextField getTxtHoraFinSabado() {
		if (txtHoraFinSabado == null) {
			txtHoraFinSabado = new JTextField();
			txtHoraFinSabado.setColumns(10);
			txtHoraFinSabado.setVisible(false);
		}
		return txtHoraFinSabado;
	}

	private JPanel getPnModificarDomingo() {
		if (pnModificarDomingo == null) {
			pnModificarDomingo = new JPanel();
			pnModificarDomingo.setLayout(new GridLayout(0, 2, 0, 0));
			pnModificarDomingo.add(getLblInstalacionDomingo());
			pnModificarDomingo.add(getCmbInstalacionesDomingo());
			pnModificarDomingo.add(getLblHoraFinDomingo());
			pnModificarDomingo.add(getTxtHoraFinDomingo());
		}
		return pnModificarDomingo;
	}

	private JLabel getLblInstalacionDomingo() {
		if (lblInstalacionDomingo == null) {
			lblInstalacionDomingo = new JLabel("Instalaci\u00F3n:");
			lblInstalacionDomingo.setHorizontalAlignment(SwingConstants.CENTER);
			lblInstalacionDomingo.setVisible(false);
		}
		return lblInstalacionDomingo;
	}

	private JComboBox<Instalacion> getCmbInstalacionesDomingo() {
		if (cmbInstalacionesDomingo == null) {
			cmbInstalacionesDomingo = new JComboBox<Instalacion>(modeloInstalacionesDomingo);
			cmbInstalacionesDomingo.setEnabled(true);
			cmbInstalacionesDomingo.setVisible(false);
		}
		return cmbInstalacionesDomingo;
	}

	private JLabel getLblHoraFinDomingo() {
		if (lblHoraFinDomingo == null) {
			lblHoraFinDomingo = new JLabel("Hora fin:");
			lblHoraFinDomingo.setHorizontalAlignment(SwingConstants.CENTER);
			lblHoraFinDomingo.setVisible(false);
		}
		return lblHoraFinDomingo;
	}

	private JTextField getTxtHoraFinDomingo() {
		if (txtHoraFinDomingo == null) {
			txtHoraFinDomingo = new JTextField();
			txtHoraFinDomingo.setColumns(10);
			txtHoraFinDomingo.setVisible(false);
		}
		return txtHoraFinDomingo;
	}

	private JPanel getPnDecisionesHoraFin() {
		if (pnDecisionesHoraFin == null) {
			pnDecisionesHoraFin = new JPanel();
			pnDecisionesHoraFin.setLayout(new GridLayout(0, 2, 0, 0));
			pnDecisionesHoraFin.add(getPnMismaHoraFin());
			pnDecisionesHoraFin.add(getPnDistintaHora());
		}
		return pnDecisionesHoraFin;
	}

	private JPanel getPnMismaHoraFin() {
		if (pnMismaHoraFin == null) {
			pnMismaHoraFin = new JPanel();
			pnMismaHoraFin.setLayout(new GridLayout(0, 1, 0, 0));
			pnMismaHoraFin.add(getRdbtnMismaHoraFin());
			pnMismaHoraFin.add(getTxtHoraFin());
		}
		return pnMismaHoraFin;
	}

	private JRadioButton getRdbtnMismaHoraFin() {
		if (rdbtnMismaHoraFin == null) {
			rdbtnMismaHoraFin = new JRadioButton("Misma hora de fin para todas las planificadas");
			rdbtnMismaHoraFin.setSelected(true);
			rdbtnMismaHoraFin.setHorizontalAlignment(SwingConstants.CENTER);
			rdbtnMismaHoraFin.setSelected(true);
			rdbtnMismaHoraFin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					txtHoraFin.setVisible(true);
					txtHoraFinLunes.setVisible(false);
					txtHoraFinMartes.setVisible(false);
					txtHoraFinMiercoles.setVisible(false);
					txtHoraFinJueves.setVisible(false);
					txtHoraFinViernes.setVisible(false);
					txtHoraFinSabado.setVisible(false);
					txtHoraFinDomingo.setVisible(false);

					lblHoraFinLunes.setVisible(false);
					lblHoraFinMartes.setVisible(false);
					lblHoraFinMiercoles.setVisible(false);
					lblHoraFinJueves.setVisible(false);
					lblHoraFinViernes.setVisible(false);
					lblHoraFinSabado.setVisible(false);
					lblHoraFinDomingo.setVisible(false);
				}
			});
			horasGroup.add(rdbtnMismaHoraFin);
		}
		return rdbtnMismaHoraFin;
	}

	private JPanel getPnDistintaHora() {
		if (pnDistintaHora == null) {
			pnDistintaHora = new JPanel();
			pnDistintaHora.add(getRdbtnDistintaHora());
		}
		return pnDistintaHora;
	}

	private JRadioButton getRdbtnDistintaHora() {
		if (rdbtnDistintaHora == null) {
			rdbtnDistintaHora = new JRadioButton("Decidir la hora de fin para cada d\u00EDa de la semana");
			rdbtnDistintaHora.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtHoraFin.setVisible(false);
					if (chckbxLunes.isSelected()) {
						lblHoraFinLunes.setVisible(true);
						txtHoraFinLunes.setVisible(true);
					}
					if (chckbxMartes.isSelected()) {
						lblHoraFinMartes.setVisible(true);
						txtHoraFinMartes.setVisible(true);
					}
					if (chckbxMiercoles.isSelected()) {
						txtHoraFinMiercoles.setVisible(true);
						lblHoraFinMiercoles.setVisible(true);
					}
					if (chckbxJueves.isSelected()) {
						txtHoraFinJueves.setVisible(true);
						lblHoraFinJueves.setVisible(true);
					}
					if (chckbxViernes.isSelected()) {
						txtHoraFinViernes.setVisible(true);
						lblHoraFinViernes.setVisible(true);
					}
					if (chckbxSabado.isSelected()) {
						lblHoraFinSabado.setVisible(true);
						txtHoraFinSabado.setVisible(true);
					}
					if (chckbxDomingo.isSelected()) {
						txtHoraFinDomingo.setVisible(true);
						lblHoraFinDomingo.setVisible(true);
					}
				}
			});
			horasGroup.add(rdbtnDistintaHora);
		}
		return rdbtnDistintaHora;
	}

	private JTextField getTxtHoraFin() {
		if (txtHoraFin == null) {
			txtHoraFin = new JTextField();
			int hora = horaInicio + 1;
			txtHoraFin.setText("" + hora);
			txtHoraFin.setColumns(10);
		}
		return txtHoraFin;
	}
	
	private void checkConflictos(ActividadPlanificada f) {
		List<ActividadPlanificada> actividadesConflicting = new ArrayList<>();
		for(int i = f.getHoraInicio(); i < f.getHoraFin() - 1; i++) {
			actividadesConflicting.addAll(
					getPrograma().getActividadesPlanificadas(f.getCodigoInstalacion(), i, f.getDia(), f.getMes(), f.getAño()));
		}
		
		for(ActividadPlanificada a: actividadesConflicting) {
			try {
				getPrograma().crearConflicto(f, a);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Ha ocurrido un error creando los conflictos");
			}
		}
	}
}